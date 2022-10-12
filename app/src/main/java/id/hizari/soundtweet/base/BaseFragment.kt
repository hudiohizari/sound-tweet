package id.hizari.soundtweet.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import id.hizari.soundtweet.util.NavigationCommand

/**
 * Sound Tweet - id.hizari.common.base
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

abstract class BaseFragment : Fragment() {

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    abstract fun getViewModel(): ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation(getViewModel())
    }

    private fun observeNavigation(viewModel: ViewModel) {
        if (viewModel is BaseViewModel) {
            viewModel.navigation.observe(viewLifecycleOwner) {
                it?.getContentIfNotHandled()?.let { command -> handleCommand(command)}
            }
        } else if (viewModel is BaseContextViewModel) {
            viewModel.navigation.observe(viewLifecycleOwner) {
                it?.getContentIfNotHandled()?.let { command -> handleCommand(command)}
            }
        }
    }

    private fun handleCommand(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> findNavController().navigate(
                command.directions,
                getExtras()
            )
            is NavigationCommand.Back -> navigateUp()
        }
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    protected fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    protected fun navigate(action: NavDirections, bundle: Bundle) {
        findNavController().navigate(action.actionId, bundle)
    }

}