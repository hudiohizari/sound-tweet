package id.hizari.common.extension

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import id.hizari.common.R
import id.hizari.common.util.STLog

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
        try {
            val ss = SpannableString(originText)
            for (i in highlightedTexts.indices) {
                val highlightedText = highlightedTexts[i] ?: context.getString(R.string.invalid)
                val color = highlightedColors[i] ?: ContextCompat.getColor(context, R.color.pale_sky)
                val span = ot.indexOf(highlightedText)
                ss.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, color)),
                    span,
                    span + highlightedText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                if (isBold) {
                    ss.setSpan(
                        StyleSpan(Typeface.BOLD),
                        span,
                        span + highlightedText.length,
                        0
                    )
                }
            }
            setText(ss, TextView.BufferType.SPANNABLE)
        } catch (e: Exception) {
            STLog.e("Error = ${e.message}")
        }
    }
}

fun TextView.setupClickableText(
    originText: String,
    clickableTexts: Array<String?>,
    onClicks: Array<() -> Unit>,
    isBold: Boolean = false
) {
    try {
        val ss = SpannableString(originText)
        for (i in clickableTexts.indices) {
            val clickableText = clickableTexts[i] ?: context.getString(R.string.invalid)
            val onClick = onClicks[i]
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onClick()
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(context, R.color.cornflower_blue)
                    ds.isUnderlineText = false
                }
            }
            val span = originText.indexOf(clickableText)
            ss.setSpan(
                clickableSpan,
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
    } catch (e: Exception) {
        STLog.e("Error = ${e.message}")
    }
}

fun TextView.setupBoldText(
    originText: String?,
    boldTexts: Array<String?>
) {
    originText?.let { ot ->
        val ss = SpannableString(originText)
        for (i in boldTexts.indices) {
            val boldText = boldTexts[i] ?: context.getString(R.string.invalid)
            val span = ot.indexOf(boldText)
            ss.setSpan(
                StyleSpan(Typeface.BOLD),
                span,
                span + boldText.length,
                0
            )
        }
    }
}