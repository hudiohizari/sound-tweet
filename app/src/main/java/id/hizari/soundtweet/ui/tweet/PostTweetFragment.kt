package id.hizari.soundtweet.ui.tweet

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.toast
import id.hizari.common.util.Resources
import id.hizari.common.R as commonR
import id.hizari.common.util.STLog
import id.hizari.common.util.Timer
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentPostTweetBinding
import id.hizari.soundtweet.extention.handleGeneralError
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class PostTweetFragment : BaseFragment() {

    private lateinit var binding: FragmentPostTweetBinding
    private val viewModel: PostTweetViewModel by viewModels()
    private val args: PostTweetFragmentArgs by navArgs()

    private val timer by lazy {
        Timer(object : Timer.Listener {
            override fun onTimerTick(duration: String) {
                viewModel.recordDuration.postValue(duration)
            }
        })
    }

    private val requestRecordAudioLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        STLog.d("Permission is ${if (isGranted) "granted" else "denied"}")
        if (isGranted) {
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
        } else toast(getString(commonR.string.please_grant_audio_permission))
    }

    private val recorder by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(requireContext())
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }
    }

    private val dirPath by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/"
    }
    private var fileName = ""

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

        initArgs()
        initObserver()
    }

    private fun initArgs() {
        STLog.d("args.tweetId = ${args.tweetId}")
        viewModel.replyingTweetId.postValue(if (args.tweetId == -1L) null else args.tweetId)
    }

    private fun initObserver() {
        viewModel.setListener(object : PostTweetViewModel.Listener {
            override fun requestAudioPermission() {
                checkAudioPermission()
            }

            override fun getFile(): File {
                return File("$dirPath$fileName")
            }
        })
        viewModel.recordingStatus.observe(viewLifecycleOwner) {
            when (it) {
                PostTweetViewModel.RECORDING_STATUS_FIRST_TIME -> startRecord()
                PostTweetViewModel.RECORDING_STATUS_RESUME -> resumeRecord()
                PostTweetViewModel.RECORDING_STATUS_PAUSE -> pauseRecord()
                PostTweetViewModel.RECORDING_STATUS_STOP -> stopRecord()
                else -> STLog.e("Unhandled recordingStatus = $it")
            }
        }
        viewModel.uploadedFileResource.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> STLog.d("Loading")
                is Resources.Success -> {
                    STLog.d("Success = ${it.data?.url}")
                    viewModel.postTweet(requireContext(), it.data?.url)
                }
                is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                else -> STLog.e("Unhandled resource")
            }
        }
        viewModel.tweetResource.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Loading -> STLog.d("Loading")
                is Resources.Success -> {
                    STLog.d("Success = ${it.data?.caption}")
                    toast(getString(R.string.tweet_posted_successfully))
                    navigateUp()
                }
                is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                else -> STLog.e("Unhandled resource")
            }
        }
    }

    private fun checkAudioPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        val isGranted = ActivityCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
        STLog.d("Checking permission = $permission")

        if (isGranted) {
            STLog.d("$permission is already granted")
            audioPermissionGranted()
        } else {
            STLog.d("Requesting permission = $permission")
            requestRecordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    private fun audioPermissionGranted() {
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

    private fun startRecord() {
        STLog.d("startRecord")
        timer.start()
        recorder.apply {
            val sdf = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.getDefault())
            val date = sdf.format(Date())
            fileName = "sound_tweet_record_$date.mp3"

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