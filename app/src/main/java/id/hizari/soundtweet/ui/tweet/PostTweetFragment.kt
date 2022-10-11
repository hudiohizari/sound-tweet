package id.hizari.soundtweet.ui.tweet

import android.Manifest
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import id.hizari.common.extension.toast
import id.hizari.common.R as commonR
import id.hizari.common.util.STLog
import id.hizari.common.util.Timer
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentPostTweetBinding
import id.hizari.soundtweet.ui.navigation.NavigationViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class PostTweetFragment : BaseFragment() {

    private lateinit var binding: FragmentPostTweetBinding

    private val sharedViewModel: NavigationViewModel by activityViewModels()

    private val viewModel: PostTweetViewModel by viewModels()

    private val timer by lazy {
        Timer(object : Timer.Listener {
            override fun onTimerTick(duration: String) {
                viewModel.recordDuration.postValue(duration)
            }
        })
    }

    private val recorder by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(requireContext())
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onPause() {
        STLog.d("onPause")
        when (viewModel.recordingStatus.value) {
            PostTweetViewModel.RECORDING_STATUS_RESUME,
            PostTweetViewModel.RECORDING_STATUS_FIRST_TIME -> {
                viewModel.recordingStatus.postValue(PostTweetViewModel.RECORDING_STATUS_PAUSE)
            }
        }

        super.onPause()
    }

    override fun onDestroy() {
        STLog.d("onDestroy")
        when (viewModel.recordingStatus.value) {
            PostTweetViewModel.RECORDING_STATUS_RESUME,
            PostTweetViewModel.RECORDING_STATUS_FIRST_TIME,
            PostTweetViewModel.RECORDING_STATUS_PAUSE -> {
                stopRecord()
            }
        }

        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post_tweet,
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
        sharedViewModel.isPermissionGranted.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    when (viewModel.recordingStatus.value) {
                        PostTweetViewModel.RECORDING_STATUS_FIRST_TIME,
                        PostTweetViewModel.RECORDING_STATUS_PAUSE -> {
                            viewModel.recordingStatus.postValue(PostTweetViewModel.RECORDING_STATUS_RESUME)
                        }
                        null -> {
                            viewModel.recordingStatus.postValue(PostTweetViewModel.RECORDING_STATUS_FIRST_TIME)
                        }
                        else -> STLog.e("Unhandled recordingStatus = ${viewModel.recordingStatus.value}")
                    }
                }
                false -> toast(getString(commonR.string.please_grant_audio_permission))
                else -> STLog.e("Unhandled isPermissionGranted = $it")
            }
            if (it != null) sharedViewModel.isPermissionGranted.postValue(null)
        }
        viewModel.listenerToFragment.observe(viewLifecycleOwner) {
            when (it) {
                PostTweetViewModel.LISTENER_REQUEST_PERMISSION -> {
                    sharedViewModel.requestedPermission.postValue(Manifest.permission.RECORD_AUDIO)
                }
                else -> STLog.e("Unhandled listener to fragment = $it")
            }
        }
        viewModel.recordingStatus.observe(viewLifecycleOwner) {
            when (it) {
                PostTweetViewModel.RECORDING_STATUS_FIRST_TIME -> startRecord()
                PostTweetViewModel.RECORDING_STATUS_RESUME -> resumeRecord()
                PostTweetViewModel.RECORDING_STATUS_PAUSE -> pauseRecord()
                PostTweetViewModel.RECORDING_STATUS_STOP -> stopRecord()
                else -> STLog.e("Unhandled recordingStatus = $it")
            }
        }
    }

    private fun startRecord() {
        STLog.d("startRecord")
        timer.start()
        recorder.apply {
            val dirPath = "${requireActivity().externalCacheDir?.absolutePath}/"
            val sdf = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.getDefault())
            val date = sdf.format(Date())
            val fileName = "sound_tweet_record_$date.mp3"

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$fileName")

            try {
                prepare()
                start()
            } catch (e: Exception) {
                STLog.e("Error = ${e.message}")
            }
        }
    }

    private fun resumeRecord() {
        STLog.d("resumeRecord")
        timer.start()
        recorder.resume()
    }

    private fun pauseRecord() {
        STLog.d("pauseRecord")
        timer.pause()
        recorder.pause()
    }

    private fun stopRecord() {
        STLog.d("stopRecord")
        timer.stop()
        recorder.stop()
        viewModel.recordingStatus.postValue(null)
        viewModel.recordDuration.postValue(null)
    }

}