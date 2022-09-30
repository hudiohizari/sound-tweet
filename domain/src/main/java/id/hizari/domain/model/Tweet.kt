package id.hizari.domain.model

import android.content.Context
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
    val likes: Long?,
    val comments: Long?,
    val plays: Long?,
    val friendsLike: MutableList<String?>?
) {

    fun getLikedByFriends(context: Context): String {
        var liked = ""
        friendsLike?.forEachIndexed { index, s ->
            liked += when (index) {
                0 -> s
                friendsLike.lastIndex -> ", ${context.getString(R.string.and)} $s"
                else -> ", $s"
            }
        }
        return liked
    }

}