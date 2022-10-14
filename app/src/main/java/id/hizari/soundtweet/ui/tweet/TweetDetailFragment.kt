package id.hizari.soundtweet.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.addDividerItem
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.list.UnspecifiedTypeItem
import id.hizari.common.list.item.DefaultEmptyListItem
import id.hizari.common.list.performUpdates
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseTweetListFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentTweetDetailBinding
import id.hizari.soundtweet.extention.handleGeneralError

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */


@AndroidEntryPoint
class TweetDetailFragment : BaseTweetListFragment() {

    private lateinit var binding: FragmentTweetDetailBinding
    private val viewModel: TweetDetailViewModel by viewModels()
    private val args: TweetDetailFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getTweetAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
            binding.rvTweet.addDividerItem()
            binding.rvTweet.itemAnimator = null
        }

        return binding.adapter as FastItemAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tweet_detail,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgument()
        initObserver()
    }

    private fun initArgument() {
        args.tweet?.let {
            viewModel.getTweet(requireContext(), it.id)
        } ?: STLog.e("Args tweet is null")
    }

    private fun initObserver() {
        viewModel.apply {
            setListener(object : TweetDetailViewModel.Listener {
                override fun toggleMedia(tweet: Tweet) {
                    toggleAudio(tweet, lastList)
                }

                override fun editCaption(tweet: Tweet) {
                    EditCaptionBottomSheet.newInstance(tweet.id, tweet.caption).also { d ->
                        d.show(childFragmentManager, d.tag)
                    }
                }

                override fun openAsText(tweet: Tweet) {
                    SeeVoiceAsTextBottomSheet.newInstance(tweet.text).also { d ->
                        d.show(childFragmentManager, d.tag)
                    }
                }
            })
            tweetResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> {
                        processLoadingGetTweet()
                        stopAudio()
                    }
                    is Resources.Success -> {
                        processSuccessGetTweet(it.data?.tweetReplies)
                        addParentTweetToList(it.data)
                        stopAudio()
                        setLikesText(it.data)
                    }
                    is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                    else -> STLog.e("Unhandled resource")
                }
            }
        }
    }

    override fun ontItemChanged(position: Int, item: Tweet) {
        if (position == 0) viewModel.tweet.postValue(item)
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
                            TweetDetailFragmentDirections.actionTweetDetailFragmentToTweetDetailFragment(
                                item.apply {
                                    isPLaying = false
                                    isBuffering = false
                                }
                            )
                        )
                    }

                    override fun onClickMenu(item: Tweet, selectedMenu: Int) {
                        EditCaptionBottomSheet.newInstance(item.id, item.caption).also { d ->
                            d.show(childFragmentManager, d.tag)
                        }
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
                    getString(R.string.empty_reply), getString(R.string.empty_reply_caption)
                )
            )
        }
        getTweetAdapter().performUpdates(items)
    }

    private fun setLikesText(tweet: Tweet?) {
        tweet?.likes?.let { likes ->
            val originText = getString(
                if (likes == "0" || likes == "1") R.string.post_like_format else {
                    R.string.post_likes_format
                },
                likes
            )
            val highlightedTexts = arrayOf(likes as String?)
            val highlightedColors = arrayOf(R.color.cinder as Int?)
            binding.tvLikes.setupHighlightedText(
                originText, highlightedTexts, highlightedColors, isBold = true
            )
        }
    }

}