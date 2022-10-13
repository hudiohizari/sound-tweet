package id.hizari.soundtweet.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.*
import id.hizari.common.list.UnspecifiedTypeItem
import id.hizari.common.list.item.DefaultEmptyListItem
import id.hizari.common.list.item.DefaultReloadListItem
import id.hizari.common.list.performUpdates
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.User
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentSearchBinding
import id.hizari.soundtweet.ui.user.UserListItem
import id.hizari.soundtweet.ui.user.UserListItemLoading

/**
 * Sound Tweet - id.hizari.soundtweet.ui.search
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
    }

    private fun initObserver() {
        viewModel.isSearchFocused.observe(viewLifecycleOwner) {
            if (it == false && binding.etSearch.hasFocus()) {
                binding.etSearch.hideKeyboard()
                if (viewModel.query.value.isNotNullOrEmpty()) {
                    viewModel.query.postValue("")
                }
            }
        }
        viewModel.query.observeDebounce(viewLifecycleOwner) {
            if (it.isNullOrBlank()) processEmptyQuery() else {
                viewModel.searchUser()
            }
        }
        viewModel.usersResource.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> processLoadingGetTweet()
                is Resources.Success -> processSuccessGetTweet(it.data)
                is Resources.Error -> processFailedGetTweet()
                else -> STLog.e("Unhandled resource")
            }
        }
        viewModel.userResource.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> STLog.d("Loading")
                is Resources.Success -> viewModel.onRefresh()
                is Resources.Error -> processFailedGetTweet()
                else -> STLog.e("Unhandled resource")
            }
        }
    }

    private fun processEmptyQuery() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultEmptyListItem(
            getString(R.string.empty_search_query),
            getString(R.string.empty_search_query_caption)
        ))
        getUserAdapter().performUpdates(items)
    }

    private fun processLoadingGetTweet() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        for (i in 1..5) {
            items.add(UserListItemLoading())
        }
        getUserAdapter().performUpdates(items)
    }

    private fun processSuccessGetTweet(list: MutableList<User>?) {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        if (list.isNotNullOrEmpty()) {
            list?.forEach {
                items.add(UserListItem(it, object : UserListItem.Listener {
                    override fun onClick(item: User) {
                        requireContext().toast("Click user = ${item.name}")
                    }
                    override fun onClickFollow(item: User) {
                        viewModel.followUser(item.id)
                    }
                }))
            }
        } else {
            items.add(
                DefaultEmptyListItem(
                    getString(R.string.empty_user),
                    getString(R.string.empty_user_caption)
                )
            )
        }
        getUserAdapter().performUpdates(items)
    }

    private fun processFailedGetTweet() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultReloadListItem(object : DefaultReloadListItem.Listener {
            override fun reload() { viewModel.searchUser() }
        }))
        getUserAdapter().performUpdates(items)
    }

    private fun getUserAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
            binding.rvUser.addDividerItem()
            binding.rvUser.itemAnimator = null
        }

        return binding.adapter as FastItemAdapter
    }

    private fun initView() {
        // Search focus listener
        binding.etSearch.setFocusListener(
            { viewModel.isSearchFocused.postValue(true) },
            { viewModel.isSearchFocused.postValue(false) }
        )

        // Set first empty view
        processEmptyQuery()
    }

}