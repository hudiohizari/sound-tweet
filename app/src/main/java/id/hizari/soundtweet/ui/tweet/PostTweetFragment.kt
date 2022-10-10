package id.hizari.soundtweet.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import id.hizari.common.util.Timer
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.base.BaseViewModel
import id.hizari.soundtweet.databinding.FragmentPostTweetBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class PostTweetFragment : BaseFragment() {

    private lateinit var binding: FragmentPostTweetBinding

    private val viewModel: PostTweetViewModel by viewModels()

    private val timer by lazy {
        Timer(object : Timer.Listener {
            override fun onTimerTick(duration: String) {
                viewModel.recordDuration.postValue(duration)
            }
        })
    }

    override fun getViewModel(): BaseViewModel = viewModel

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
        viewModel.isRecording.observe(viewLifecycleOwner) {
            if (it) timer.start() else timer.pause()
        }
    }

}