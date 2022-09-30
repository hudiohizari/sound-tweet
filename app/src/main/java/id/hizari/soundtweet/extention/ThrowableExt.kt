package id.hizari.soundtweet.extention

import android.view.View
import id.hizari.common.extension.snackbar
import id.hizari.common.extension.toast
import id.hizari.data.network.util.ConnectionException

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun Throwable.handleGeneralError(view: View) {
    when (this) {
        is ConnectionException -> view.snackbar(message, isWithCloseButton = true)
        else -> view.context.toast(message)
    }
}