package id.hizari.soundtweet.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.extension.observeDebounce
import id.hizari.common.extension.setupClickableText
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.databinding.FragmentLoginBinding
import id.hizari.soundtweet.extention.handleGeneralError

/**
 * Sound Tweet - id.hizari.soundtweet.ui.auth
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewModel(): ViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
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
        initView()
    }

    private fun initObserver() {
        viewModel.apply {
            username.observeDebounce(viewLifecycleOwner) { checkButton() }
            password.observeDebounce(viewLifecycleOwner) { checkButton() }
            userResource.observe(viewLifecycleOwner) {
                when (it) {
                    is Resources.Loading -> STLog.d("Loading")
                    is Resources.Success -> STLog.d("Success = ${it.data?.name}")
                    is Resources.Error -> it.throwable?.handleGeneralError(binding.clRoot)
                    else -> STLog.e("Unhandled resource")
                }
            }
        }
    }

    private fun initView() {
        binding.tvRegisterNow.apply {
            val originText = getString(R.string.do_not_have_an_account_yet)
            val clickableTexts = arrayOf(
                getString(R.string.do_not_have_an_account_yet_highlighted) as String?
            )
            val onClicks = arrayOf({
                navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                )
            })
            setupClickableText(originText, clickableTexts, onClicks, isBold = true)
        }
    }
}