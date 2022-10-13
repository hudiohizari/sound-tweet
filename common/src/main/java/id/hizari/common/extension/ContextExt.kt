package id.hizari.common.extension

import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

fun Context.showSimpleDialogReturn(
    message: String?,
    title: String? = null
): MaterialAlertDialogBuilder {
    val builder = MaterialAlertDialogBuilder(this)
    if (title != null) {
        builder.setTitle(title)
    }
    builder.setMessage(message ?: getString(R.string.failed_to_show_message))
    builder.setCancelable(false)
    return builder
}