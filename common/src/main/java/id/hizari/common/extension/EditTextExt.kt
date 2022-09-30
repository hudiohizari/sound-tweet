package id.hizari.common.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import id.hizari.common.util.STLog


/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

fun EditText.setFocusListener(
    doOnHasFocus: () -> Unit,
    doOnLostFocus: () -> Unit,
)  {

    setOnFocusChangeListener { _, b ->
        if (b) doOnHasFocus() else doOnLostFocus()
    }

    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus()
        }
        false
    }

}