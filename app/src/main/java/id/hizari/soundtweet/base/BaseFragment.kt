package id.hizari.soundtweet.base

import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import id.hizari.soundtweet.extention.popBackStackAllInstances

/**
 * Sound Tweet - id.hizari.common.base
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

abstract class BaseFragment : Fragment() {
    private var isNavigated = false

    protected fun navigateWithAction(action: NavDirections) {
        isNavigated = true
        findNavController().navigate(action)
    }

    protected fun navigate(resId: Int) {
        isNavigated = true
        findNavController().navigate(resId)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    protected fun getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

    protected fun setNavigationResult(result: String?, key: String = "result") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isNavigated)
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                val navController = findNavController()
                navController.currentBackStackEntry?.destination?.id?.let {
                    findNavController().popBackStackAllInstances(it, true)
                } ?: navController.popBackStack()
            }
    }

}