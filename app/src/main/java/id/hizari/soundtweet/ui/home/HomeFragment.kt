package id.hizari.soundtweet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.R
import id.hizari.soundtweet.databinding.FragmentHomeBinding
import id.hizari.soundtweet.extention.handleGeneralError

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
                is Resources.Loading -> STLog.d("Loading")
                is Resources.Success -> STLog.d("List size = ${it.data?.size}")
                is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                else -> STLog.e("Unhandled resource")
            }
        }
    }

}