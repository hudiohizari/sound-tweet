package id.hizari.soundtweet.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
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
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityNavigationBinding>(
            this,
            R.layout.activity_navigation
        ).apply {
            lifecycleOwner = this@NavigationActivity
        }
    }
    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fcvMain
        ) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        // Setup the bottom navigation view with navController
        binding.bnvMain.setupWithNavController(navController)
    }
}