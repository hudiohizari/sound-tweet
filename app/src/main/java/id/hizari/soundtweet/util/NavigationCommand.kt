package id.hizari.soundtweet.util

import androidx.navigation.NavDirections

/**
 * Sound Tweet - id.hizari.soundtweet.util
 *
 * Created by Hudio Hizari on 10/10/2022.
 * https://github.com/hudiohizari
 *
 */

sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
}