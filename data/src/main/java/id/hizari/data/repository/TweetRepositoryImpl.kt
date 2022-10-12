package id.hizari.data.repository

import android.content.Context
import id.hizari.data.local.DataStore
import id.hizari.data.network.model.request.PostTweetRequest
import id.hizari.data.network.service.TweetService
import id.hizari.data.network.service.UserService
import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetRepositoryImpl @Inject constructor(
    private val tweetService: TweetService,
    private val dataStore: DataStore
): TweetRepository, SafeApiRequest() {

    override suspend fun postTweet(
        context: Context,
        caption: String?,
        postUrl: String?,
        text: String?
    ): Tweet? {
        val request = PostTweetRequest(caption, postUrl, text)
        val response = apiRequest { tweetService.postTweet(request) }
        return response?.toDomain(context, dataStore.getLoggedInUser().first()?.id)
    }

    override suspend fun getTweets(
        context: Context
    ): MutableList<Tweet>? {
        val response = apiRequest { tweetService.getTweets() }
        return response?.map {
            it.toDomain(context, dataStore.getLoggedInUser().first()?.id)
        }?.toMutableList()
    }

}