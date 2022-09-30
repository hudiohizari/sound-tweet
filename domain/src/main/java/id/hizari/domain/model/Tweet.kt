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

class Tweet(
    val id: Int?,
    val imgUrl: String?,
    val name: String?,
    val userName: String?,
    val postedTime: String?,
    val caption: String?,
    val mediaUrl: String?,
    val mediaDuration: Int?,
    val isLiked: Boolean?,
    val likes: Long?,
    val comments: Long?,
    val plays: Long?,
    val friendsLike: MutableList<String?>?
) {

    fun isShowLikedByFriend(): Boolean {
        return friendsLike?.isEmpty() == false
    }

    fun getLikedByFriends(context: Context): String {
        val size = friendsLike?.size ?: 0
        return if (size == 1) {
            context.getString(
                R.string.friend_liked,
                friendsLike?.get(0)
            )
        } else if (size == 2) {
            context.getString(
                R.string.friends_liked,
                friendsLike?.get(0),
                friendsLike?.get(1)
            )
        } else if (size > 2) {
            context.getString(
                R.string.many_friends_liked,
                friendsLike?.get(0),
                friendsLike?.get(1),
                (size - 2)
            )
        } else ""
    }

    fun getFormattedLikes(): String {
        return likes?.toCompactFormat() ?: ""
    }

    fun getFormattedComments(): String {
        return comments?.toCompactFormat() ?: ""
    }

    fun getFormattedPlays(): String {
        return plays?.toCompactFormat() ?: ""
    }

    fun getPostedTimeAgo(context: Context): String {
        return DateUtil.getTimeAgo(context, postedTime)
    }

}