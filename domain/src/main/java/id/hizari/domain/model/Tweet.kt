package id.hizari.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Sound Tweet - id.hizari.domain.model
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

@Parcelize
data class Tweet(
    val caption: String?,
    val postedTimeAgo: String?,
    val timeAndDate: String?,
    val id: Long?,
    val postUrl: String?,
    val tweetReplies: MutableList<Tweet>?,
    val replies: String?,
    val likes: String?,
    val isLiked: Boolean?,
    var isPLaying: Boolean?,
    var isBuffering: Boolean?,
    var isOwnTweet: Boolean?,
    val user: User?
): Parcelable