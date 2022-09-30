package id.hizari.common.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar
import id.hizari.common.R
import id.hizari.common.util.STLog

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */
 
fun View.snackbar(
    message: String?,
    isWithCloseButton: Boolean = false
) {
    try {
        Snackbar.make(
            this,
            message ?: context.getString(R.string.empty_text),
            if (isWithCloseButton) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        ).also { snackbar ->
            if (isWithCloseButton) {
                snackbar.setAction(context.getString(android.R.string.ok)) {
                    snackbar.dismiss()
                }
            }
        }.show()
    } catch (e: Exception) {
        STLog.e("Error = ${e.message}")
    }
}