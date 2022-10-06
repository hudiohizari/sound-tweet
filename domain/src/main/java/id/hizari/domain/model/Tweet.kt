package id.hizari.domain.model

import android.content.Context
import id.hizari.common.extension.toCompactFormat
import id.hizari.common.util.DateUtil
import id.hizari.domain.R

/**
 * Sound Tweet - id.hizari.domain.model
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

data class Tweet(
    val caption: String?,
    val postedTimeAgo: String?,
    val id: Int?,
    val postUrl: String?,
    val updatedAt: String?,
    val user: User?
)