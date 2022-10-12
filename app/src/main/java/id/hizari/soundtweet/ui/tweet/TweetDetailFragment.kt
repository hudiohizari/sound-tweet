package id.hizari.soundtweet.ui.tweet

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
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

    private var isNeverPlayed = true
    private var length = 0

    override fun getViewModel(): ViewModel = viewModel

    override fun onPause() {
        super.onPause()

        if (mediaPlayer.isPlaying) {
            pauseAudio()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

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
            viewModel.tweetResource.postValue(Resources.Success(it))
            viewModel.getTweet(requireContext(), it.id)
        }
    }

    private fun initObserver() {
        viewModel.apply {
            setListener(object : TweetDetailViewModel.Listener {
                override fun toggleMedia() {
                    when {
                        isNeverPlayed -> playAudio(viewModel.tweetResource.value?.data?.postUrl)
                        mediaPlayer.isPlaying -> pauseAudio()
                        else -> resumeAudio()
                    }
                }
            })
            tweetResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> STLog.d("Loading")
                    is Resources.Success -> {
                        STLog.d("Success")
                        it.data?.likes?.let { likes ->
                            val originText = getString(
                                if (likes == "1") R.string.post_like_format else {
                                    R.string.post_likes_format
                                },
                                likes
                            )
                            val highlightedTexts = arrayOf("$likes" as String?)
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
                viewModel.isBuffering.postValue(percentage < 100)
            }
            setOnCompletionListener {
                STLog.d("complete")
                stopAudio()
            }
        }
    }

    private fun playAudio(url: String?) {
        STLog.d("playAudio")
        url?.let {
            try {
                mediaPlayer.apply {
                    reset()
                    setDataSource(it)
                    prepare()
                    start()
                }
            } catch (e: Exception) {
                STLog.e("${e.message}")
            }
        } ?: STLog.e("URL is null")
        isNeverPlayed = false
        viewModel.isPlaying.postValue(true)
    }

    private fun resumeAudio() {
        STLog.d("resumeAudio")
        mediaPlayer.apply {
            if (!isPlaying) {
                seekTo(length)
                start()
            }
        }
        viewModel.isPlaying.postValue(true)
    }

    private fun pauseAudio() {
        STLog.d("pauseAudio")
        mediaPlayer.apply {
            if (isPlaying) {
                pause()
                length = currentPosition
            }
        }
        viewModel.isPlaying.postValue(false)
    }

    private fun stopAudio() {
        STLog.d("stopAudio")
        mediaPlayer.apply {
            if (isPlaying) stop()
            length = 0
        }
        viewModel.isPlaying.postValue(false)
    }

}