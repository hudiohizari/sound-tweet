package id.hizari.common.extension

import android.content.Context
import android.widget.Toast
import id.hizari.common.R

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun Context.toast(
    message: String?,
    isLong: Boolean = false
) {
    Toast.makeText(
        this,
        message ?: getString(R.string.empty_text),
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}