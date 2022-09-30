package id.hizari.common.extension

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import id.hizari.common.R

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun TextView.setupHighlightedText(
    originText: String?,
    highlightedTexts: Array<String?>,
    highlightedColors: Array<Int?>,
    isBold: Boolean = false
) {
    originText?.let { ot ->
        val ss = SpannableString(originText)
        for (i in highlightedTexts.indices) {
            val clickableText = highlightedTexts[i] ?: context.getString(R.string.invalid)
            val color = highlightedColors[i] ?: ContextCompat.getColor(context, R.color.pale_sky)
            val span = ot.indexOf(clickableText)
            ss.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, color)),
                span,
                span + clickableText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (isBold) {
                ss.setSpan(
                    StyleSpan(Typeface.BOLD),
                    span,
                    span + clickableText.length,
                    0
                )
            }
        }
        setText(ss, TextView.BufferType.SPANNABLE)
        movementMethod = LinkMovementMethod.getInstance()
        isClickable = true
    }
}