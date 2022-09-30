package id.hizari.soundtweet.extention

import androidx.navigation.NavController

/**
 * Sound Tweet - id.hizari.soundtweet.extention
 *
 * Created by Hudio Hizari on 28/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}