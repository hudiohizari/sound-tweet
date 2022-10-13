package id.hizari.soundtweet.ui.tweet

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
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
class TweetDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentTweetDetailBinding
    private val viewModel: TweetDetailViewModel by viewModels()
    private val args: TweetDetailFragmentArgs by navArgs()

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }

    private var lastPlayPosition: Int = 0

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onPause() {
        super.onPause()

        stopAudio()
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
            viewModel.tweet.postValue(it)
            viewModel.lastId = it.id
        }
    }

    private fun initObserver() {
        viewModel.apply {
            setListener(object : TweetDetailViewModel.Listener {
                override fun toggleMedia() {
                    tweet.value?.let { toggleAudio(it) } ?: STLog.e("Tweet is null")
                }
            })
            tweetResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Success -> lastPlayPosition = 0
                    else -> STLog.e("Unhandled resource = $it")
                }
            }
            tweetResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> {
                        STLog.d("Loading")
                        stopAudio()
                    }
                    is Resources.Success -> {
                        STLog.d("Success")
                        it.data?.likes?.let { likes ->
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
                    is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                    else -> STLog.e("Unhandled resource")
                }
            }
        }
        mediaPlayer.apply {
            setOnBufferingUpdateListener { _, percentage ->
                STLog.d("Buffering = $percentage")
                viewModel.tweet.value?.let {
                    viewModel.tweet.postValue(it.apply {
                        isBuffering = percentage < 100
                    })
                }
            }
            setOnCompletionListener {
                STLog.d("Audio completed")
                viewModel.tweet.value?.let {
                    viewModel.tweet.postValue(it.apply {
                        isPLaying = false
                    })
                }
                lastPlayPosition = 0
            }
            setOnPreparedListener {
                start()
                seekTo(lastPlayPosition)
            }
        }
    }

    private fun toggleAudio(item: Tweet) {
        viewModel.tweet.postValue(
            item.apply {
                isPLaying = !(isPLaying ?: false)
                when (isPLaying) {
                    true -> {
                        STLog.d("Play audio")
                        postUrl?.let {
                            try {
                                mediaPlayer.apply {
                                    reset()
                                    setDataSource(it)
                                    prepareAsync()
                                    isBuffering = true
                                }
                            } catch (e: Exception) {
                                STLog.e("${e.message}")
                                mediaPlayer.reset()
                            }
                        } ?: STLog.e("URL is null")
                    }
                    false -> stopAudio()
                    else -> STLog.e("isPlaying is empty")
                }
            }
        )
    }

    private fun stopAudio() {
        STLog.d("Stop audio")
        mediaPlayer.apply {
            if (isPlaying) {
                stop()
                lastPlayPosition = currentPosition
                STLog.d("Audio stopped")
            } else STLog.d("Audio not played")
        }
    }

}