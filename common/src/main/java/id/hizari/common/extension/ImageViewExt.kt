package id.hizari.common.extension

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import id.hizari.common.R
import id.hizari.common.util.Constant
import id.hizari.common.util.STLog

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@BindingAdapter(value = ["imageUrl", "disableCenterCrop"], requireAll = false)
fun ImageView.imageUrl(
    url: String?,
    disableCenterCrop: Boolean = false
) {
    if (url == null) {
        return
    }

    loadImageFromUrl(
        url.ifEmpty { Constant.URL.IMAGE_INVALID },
        true,
        disableCenterCrop
    )
}

@SuppressLint("CheckResult")
fun ImageView.loadImageFromUrl(
    url: String,
    withHeader: Boolean = false,
    disableCenterCrop: Boolean = false
) {
    val option = RequestOptions()
        .timeout(15000)
        .placeholder(R.color.pale_sky)
        .error(R.drawable.image_invalid_url)

    if (!disableCenterCrop) option.centerCrop()

    val glideUrl = GlideUrl(
        url,
        LazyHeaders.Builder().addHeader(
            context.getString(R.string.user_agent),
            context.getString(R.string.app_name)
        ).build()
    )

    try {
        Glide.with(context)
            .load(if (withHeader) glideUrl else url)
            .apply(option)
            .into(this)
    } catch (e: IllegalArgumentException) {
        STLog.e("Error = ${e.message}")
    }
}