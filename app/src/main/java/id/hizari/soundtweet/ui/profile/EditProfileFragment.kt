package id.hizari.soundtweet.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.observeDebounce
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseContextViewModel
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.databinding.FragmentEditProfileBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.profile
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    override fun getViewModel(): BaseContextViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_profile, container, false
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
        viewModel.apply {
            bio.observeDebounce(viewLifecycleOwner) { checkButton() }
            email.observeDebounce(viewLifecycleOwner) { checkButton() }
            name.observeDebounce(viewLifecycleOwner) { checkButton() }
            username.observeDebounce(viewLifecycleOwner) { checkButton() }
            password.observeDebounce(viewLifecycleOwner) { checkButton() }
        }
    }

}