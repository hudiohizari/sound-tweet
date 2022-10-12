package id.hizari.soundtweet.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.addDividerItem
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.common.extension.toast
import id.hizari.common.list.UnspecifiedTypeItem
import id.hizari.common.list.item.DefaultEmptyListItem
import id.hizari.common.list.item.DefaultReloadListItem
import id.hizari.common.list.performUpdates
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Notification
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentNotificationBinding

/**
 * Sound Notification - id.hizari.soundNotification.ui.notification
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationBinding

    private val viewModel: NotificationViewModel by viewModels()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notification, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initApiCall()
        initObserver()
    }

    private fun initApiCall() {
        viewModel.getNotifications()
    }

    private fun initObserver() {
        viewModel.notificationResource.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> processLoadingGetNotification()
                is Resources.Success -> processSuccessGetNotification(it.data)
                is Resources.Error -> processFailedGetNotification()
                else -> STLog.e("Unhandled resource")
            }
        }
    }

    private fun processLoadingGetNotification() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        for (i in 1..5) {
            items.add(NotificationListItemLoading())
        }
        getNotificationAdapter().performUpdates(items)
    }

    private fun processSuccessGetNotification(list: MutableList<Notification>?) {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        if (list.isNotNullOrEmpty()) {
            list?.forEach {
                items.add(NotificationListItem(it, object : NotificationListItem.Listener {
                    override fun onClick(item: Notification) {
                        toast("Clicked ${item.caption}")
                    }
                }))
            }
        } else {
            items.add(
                DefaultEmptyListItem(
                    getString(R.string.empty_notification),
                    getString(R.string.empty_notification_caption)
                )
            )
        }
        getNotificationAdapter().performUpdates(items)
    }

    private fun processFailedGetNotification() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultReloadListItem(object : DefaultReloadListItem.Listener {
            override fun reload() {
                viewModel.getNotifications()
            }
        }))
        getNotificationAdapter().performUpdates(items)
    }

    private fun getNotificationAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
            binding.rvNotification.addDividerItem()
            binding.rvNotification.itemAnimator = null
        }

        return binding.adapter as FastItemAdapter
    }

}