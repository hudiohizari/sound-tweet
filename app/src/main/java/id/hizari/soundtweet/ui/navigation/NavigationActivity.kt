package id.hizari.soundtweet.ui.navigation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.common.util.STLog
import id.hizari.soundtweet.R
import id.hizari.soundtweet.databinding.ActivityNavigationBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.navigation
 *
 * Created by Hudio Hizari on 26/09/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var navController: NavController
    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initView()
        initObserver()
        initCall()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initView() {
        // Setup navController
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fcvMain
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        binding.bnvMain.setupWithNavController(navController)
    }

    private fun initObserver() {
        viewModel.apply {
            val owner = this@NavigationActivity
            isLoggedInResource.observe(owner) {
                val selectedNavigation = if (it.data == true) R.id.dashboardNavigation else {
                    R.id.authNavigation
                }
                val isOkToNavigate = if (selectedNavigation == R.id.dashboardNavigation) {
                    navController.currentDestination?.id != R.id.homeFragment
                } else {
                    navController.currentDestination?.id != R.id.registerFragment
                }

                if (isOkToNavigate) {
                    navController.navigate(
                        selectedNavigation,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(navController.graph.startDestinationId, true)
                            .build()
                    )
                }
            }
            navigateTo.observe(owner) {
                it?.let { navController.navigate(it) }
            }
        }

        navController.apply {
            addOnDestinationChangedListener { _, destination, _ ->
                STLog.d("destination.id = ${destination.id}")
                viewModel.isShowFab.postValue(destination.id != R.id.postTweetFragment)
            }
        }

        binding.bnvMain.apply {
            setOnItemReselectedListener {
                STLog.d("it.title = ${it.title}")
            }
        }
    }

    private fun initCall() {
        viewModel.checkIsLoggedIn()
    }

}