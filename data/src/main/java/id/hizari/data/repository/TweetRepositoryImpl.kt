package id.hizari.data.repository

import android.content.Context
import id.hizari.common.util.STLog
import id.hizari.data.local.DataStore
import id.hizari.data.network.model.request.PostTweetRequest
import id.hizari.data.network.service.TweetService
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

    override suspend fun getHomeTweets(context: Context): MutableList<Tweet>? {
        STLog.d("Getting home tweets")
        val response = apiRequest { tweetService.getTweets() }
        return response?.map{
            it.toDomain(context, dataStore.getLoggedInUser().first()?.id)
        }?.toMutableList()
    }

    override suspend fun getOwnTweets(context: Context): MutableList<Tweet>? {
        STLog.d("Getting own tweets")
        val loggedInUser = dataStore.getLoggedInUser().first()
        val response = apiRequest {
            tweetService.getUserTweets(loggedInUser?.username?.removePrefix("@"))
        }
        return response?.map{
            it.toDomain(context, loggedInUser?.id)
        }?.toMutableList()
    }

    override suspend fun getUserTweets(
        context: Context,
        username: String?
    ): MutableList<Tweet>? {
        STLog.d("Getting $username's tweets")
        val response = apiRequest { tweetService.getUserTweets(username) }
        return response?.map{
            it.toDomain(context, dataStore.getLoggedInUser().first()?.id)
        }?.toMutableList()
    }

    override suspend fun getTweet(context: Context, id: Long?): Tweet? {
        val response = apiRequest { tweetService.getTweet(id) }
        return response?.toDomain(context, dataStore.getLoggedInUser().first()?.id)
    }

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

    override suspend fun postReplyTweet(
        context: Context,
        id: Long?,
        caption: String?,
        postUrl: String?,
        text: String?
    ): Tweet? {
        val request = PostTweetRequest(caption, postUrl, text)
        val response = apiRequest { tweetService.postReplyTweet(id, request) }
        return response?.toDomain(context, dataStore.getLoggedInUser().first()?.id)
    }

    override suspend fun postLikeTweets(context: Context, id: Long?): Tweet? {
        val response = apiRequest { tweetService.postLikeTweet(id) }
        return response?.toDomain(context, dataStore.getLoggedInUser().first()?.id)
    }

}