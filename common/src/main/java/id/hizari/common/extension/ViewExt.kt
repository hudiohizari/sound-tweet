package id.hizari.common.extension

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
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

fun View.loadingAnimation() {
    val fadeAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.15f)
    fadeAnim.duration = 500
    fadeAnim.repeatCount = ObjectAnimator.INFINITE
    fadeAnim.repeatMode = ObjectAnimator.REVERSE
    fadeAnim.start()
    tag = fadeAnim
}

/*
* Need to improve later
* Cancelling animation immediately will cause animation stopping midway, where the view's alpha still applied
*/
fun View.stopLoadingAnimation() {
    try {
        (tag as ObjectAnimator).cancel()
        alpha = 1f
    } catch (e: Exception) {
        STLog.e("Animator might not set yet, message: ${e.message}")
    }
}

@BindingAdapter(value = ["isAnimateLoading"])
fun View.animateLoading(isAnimateLoading: Boolean?) {
    isAnimateLoading?.let { if (it) loadingAnimation() else stopLoadingAnimation() }
}

fun View?.hideKeyboard() {
    if (this != null) {
        clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}