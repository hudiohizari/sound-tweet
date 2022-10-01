package id.hizari.soundtweet.ui.home

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
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.databinding.FragmentHomeBinding
import id.hizari.soundtweet.ui.tweet.TweetListItem
import id.hizari.soundtweet.ui.tweet.TweetListItemLoading

/**
 * Sound Tweet - id.hizari.soundtweet.ui.home
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
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
    }

    private fun initObserver() {
        viewModel.tweets.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> processLoadingGetTweet()
                is Resources.Success -> processSuccessGetTweet(it.data)
                is Resources.Error -> processFailedGetTweet()
                else -> STLog.e("Unhandled resource")
            }
        }
    }

    private fun processLoadingGetTweet() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        for (i in 1..5) {
            items.add(TweetListItemLoading())
        }
        getTweetAdapter().performUpdates(items)
    }

    private fun processSuccessGetTweet(list: MutableList<Tweet>?) {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        if (list.isNotNullOrEmpty()) {
            list?.forEach {
                items.add(TweetListItem(it, object : TweetListItem.Listener {
                    override fun onClick(item: Tweet) {
                        requireContext().toast("Goto detail: ${item.user?.name}")
                    }

                    override fun onClickMedia(item: Tweet) {
                        requireContext().toast("Play media: ${item.postUrl}")
                    }

//                    override fun onClickLike(item: Tweet) {
//                        requireContext().toast("Like tweet: ${item.name}")
//                    }
                }))
            }
        } else {
            items.add(
                DefaultEmptyListItem(
                    getString(R.string.empty_tweet),
                    getString(R.string.empty_tweet_caption)
                )
            )
        }
        getTweetAdapter().performUpdates(items)
    }

    private fun processFailedGetTweet() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultReloadListItem(object : DefaultReloadListItem.Listener {
            override fun reload() {
                viewModel.getTweets()
            }
        }))
        getTweetAdapter().performUpdates(items)
    }

    private fun getTweetAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
            binding.rvTweet.addDividerItem()
        }

        return binding.adapter as FastItemAdapter
    }

}