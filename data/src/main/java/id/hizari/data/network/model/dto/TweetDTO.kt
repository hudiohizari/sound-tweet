package id.hizari.data.network.model.dto

import android.content.Context
import id.hizari.common.extension.toCompactFormat
import id.hizari.common.util.DateUtil
import id.hizari.domain.model.Tweet

data class TweetDTO(
    val caption: String?,
    val createdAt: String?,
    val id: Long?,
    val likes: Long?,
    val postUrl: String?,
    val updatedAt: String?,
    val user: UserDTO?
) {

    fun toDomain(context: Context, loggedInId: Long?): Tweet {
        return Tweet(
            caption,
            getPostedTimeAgo(context),
            id,
            postUrl,
            likes?.toCompactFormat() ?: "",
            false,
            user?.toDomain(loggedInId)
        )
    }

    private fun getPostedTimeAgo(context: Context): String {
        return DateUtil.getTimeAgo(context, createdAt)
    }

}