package id.hizari.soundtweet.ui.profile

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
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.extension.toast
import id.hizari.common.list.UnspecifiedTypeItem
import id.hizari.common.list.item.DefaultEmptyListItem
import id.hizari.common.list.item.DefaultReloadListItem
import id.hizari.common.list.performUpdates
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseTweetListFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentProfileBinding
import id.hizari.soundtweet.extention.handleGeneralError
import id.hizari.soundtweet.ui.tweet.TweetListItem
import id.hizari.soundtweet.ui.tweet.TweetListItemLoading

/**
 * Sound Tweet - id.hizari.soundtweet.ui.profile
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class ProfileFragment : BaseTweetListFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun getTweetAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
            binding.rvTweet.addDividerItem()
            binding.rvTweet.itemAnimator = null
        }

        return binding.adapter as FastItemAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCall()
        initObserver()
    }

    private fun initCall() {
        viewModel.getLocalUser()
        viewModel.getTweets(requireContext())
    }

    private fun initObserver() {
        viewModel.apply {
            userResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> STLog.d("Loading")
                    is Resources.Success -> {
                        STLog.d("Success = ${it.data?.name}")
                        binding.tvFollowing.apply {
                            val following = it.data?.following ?: "0"
                            val originText = getString(R.string.following_format, following)
                            val highlightedTexts = arrayOf(following as String?)
                            val highlightedColors = arrayOf(R.color.cinder as Int?)
                            setupHighlightedText(
                                originText, highlightedTexts, highlightedColors, isBold = true
                            )
                        }
                        binding.tvFollower.apply {
                            val follower = it.data?.follower ?: "0"
                            val originText = getString(
                                if (follower == "0" || follower == "1") {
                                    R.string.follower_format
                                } else {
                                    R.string.followers_format
                                },
                                follower
                            )
                            val highlightedTexts = arrayOf(follower as String?)
                            val highlightedColors = arrayOf(R.color.cinder as Int?)
                            setupHighlightedText(
                                originText, highlightedTexts, highlightedColors, isBold = true
                            )
                        }
                    }
                    is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                    else -> STLog.e("Unhandled resource $it")
                }
            }
            tweetsResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> {
                        processLoadingGetTweet()
                        stopAudio()
                    }
                    is Resources.Success -> {
                        processSuccessGetTweet(it.data)
                        stopAudio()
                    }
                    is Resources.Error -> processFailedGetTweet()
                    else -> STLog.e("Unhandled resource")
                }
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
        lastList = list ?: mutableListOf()
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        if (list.isNotNullOrEmpty()) {
            list?.forEach {
                items.add(TweetListItem(it, object : TweetListItem.Listener {
                    override fun onClick(item: Tweet) {
                        navigate(
                            ProfileFragmentDirections.actionProfileFragmentToTweetDetailFragment(
                                item.apply {
                                    isPLaying = false
                                    isBuffering = false
                                }
                            )
                        )
                    }

                    override fun onClickMenu(item: Tweet, selectedMenu: Int) {
                        toast("Edit")
                    }

                    override fun onClickLike(item: Tweet) {
                        viewModel.postLikeTweet(requireContext(), item.id)
                    }

                    override fun onClickPlay(item: Tweet) {
                        toggleAudio(item, list)
                    }
                }))
            }
        } else {
            items.add(
                DefaultEmptyListItem(
                    getString(R.string.empty_tweet), getString(R.string.empty_tweet_caption)
                )
            )
        }
        getTweetAdapter().performUpdates(items)
    }

    private fun processFailedGetTweet() {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultReloadListItem(object : DefaultReloadListItem.Listener {
            override fun reload() {
                viewModel.getTweets(requireContext())
            }
        }))
        getTweetAdapter().performUpdates(items)
    }

}