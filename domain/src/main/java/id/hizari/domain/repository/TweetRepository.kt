package id.hizari.domain.repository

import android.content.Context
import id.hizari.domain.model.Tweet

/**
 * Sound Tweet - id.hizari.domain.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

interface TweetRepository {

    suspend fun getTweets(context: Context): MutableList<Tweet>?
    suspend fun postTweet(
        context: Context,
        caption: String?,
        postUrl: String?,
        text: String?
    ): Tweet?
    suspend fun postLikeTweets(context: Context, id: Long?): Tweet?

}