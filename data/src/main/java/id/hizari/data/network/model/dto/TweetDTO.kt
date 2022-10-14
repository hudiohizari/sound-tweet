package id.hizari.data.network.model.dto

import android.content.Context
import id.hizari.common.extension.toCompactFormat
import id.hizari.common.util.DateUtil
import id.hizari.domain.model.Tweet

data class TweetDTO(
    val caption: String?,
    val text: String?,
    val createdAt: String?,
    val id: Long?,
    val likes: Long?,
    val postUrl: String?,
    val replies: MutableList<TweetDTO>?,
    val updatedAt: String?,
    val user: UserDTO?
) {

    fun toDomain(context: Context, loggedInId: Long?): Tweet {
        return Tweet(
            caption,
            text,
            DateUtil.getTimeAgo(context, createdAt),
            DateUtil.changeDateFormat(createdAt),
            id,
            postUrl,
            replies?.map { it.toDomain(context, loggedInId) }?.toMutableList(),
            (replies?.size ?: 0).toCompactFormat(),
            (likes ?: 0).toCompactFormat(),
            isLiked = false,
            isPLaying = false,
            isBuffering = false,
            isOwnTweet = user?.id == loggedInId,
            user = user?.toDomain(loggedInId)
        )
    }

}