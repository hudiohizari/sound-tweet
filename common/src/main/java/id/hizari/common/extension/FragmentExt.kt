package id.hizari.common.extension

import androidx.fragment.app.Fragment

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */
 
fun Fragment.toast(message: String?, isLong: Boolean = false) {
    requireContext().toast(message, isLong)
}