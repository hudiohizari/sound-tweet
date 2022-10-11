package id.hizari.soundtweet.ui.tweet

import android.Manifest
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

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onPause() {
        super.onPause()
        if (viewModel.isRecording.value == true) viewModel.isRecording.postValue(false)
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
                    viewModel.isRecording.postValue(!(viewModel.isRecording.value ?: false))
                }
                false -> toast(getString(commonR.string.please_grant_audio_permission))
                else -> STLog.e("Unhandled isPermissionGranted")
            }
        }
        viewModel.listenerToFragment.observe(viewLifecycleOwner) {
            when (it) {
                PostTweetViewModel.LISTENER_REQUEST_PERMISSION -> {
                    sharedViewModel.requestedPermission.postValue(Manifest.permission.RECORD_AUDIO)
                }
                else -> STLog.e("Unhandled listener to fragment")
            }
        }
        viewModel.isRecording.observe(viewLifecycleOwner) {
            if (it) startRecord() else pauseRecord()
        }
    }

    private fun startRecord() {
        timer.start()
    }

    private fun pauseRecord() {
        timer.pause()
    }

}