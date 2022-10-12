package id.hizari.data.network.model.request

/**
 * Sound Tweet - id.hizari.data.network.model.request
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class PostTweetRequest(
    val caption: String?,
    val postUrl: String?,
    val text: String?
)