package id.hizari.domain.model

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
    val id: Long?,
    val postUrl: String?,
    val likes: String?,
    val isLiked: Boolean?,
    val user: User?
)