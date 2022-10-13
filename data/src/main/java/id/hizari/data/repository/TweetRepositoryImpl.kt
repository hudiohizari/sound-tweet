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

    override suspend fun getTweets(context: Context, isHome: Boolean): MutableList<Tweet>? {
        val loggedInUser = dataStore.getLoggedInUser().first()
        val list = mutableListOf<Tweet>().apply {
            apiRequest { tweetService.getTweets() }?.let { tweets ->
                STLog.d("Getting tweets from ${loggedInUser?.username?.removePrefix("@")}")
                for (tweet in tweets) {
                    add(tweet.toDomain(context, loggedInUser?.id))
                }
            }
            if (isHome) {
                loggedInUser?.userFollowingUsername?.let { usernames ->
                    for (username in usernames) {
                        STLog.d("Getting tweets from $username")
                        val response = apiRequest { tweetService.getTweets(username) }
                        response?.let { tweets ->
                            for (tweet in tweets) {
                                add(tweet.toDomain(context, loggedInUser.id))
                            }
                        }
                    }
                }
                sortByDescending { it.id }
            }
        }
        return if (isHome) list.distinctBy { it.id }.toMutableList() else list
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

    override suspend fun postLikeTweets(context: Context, id: Long?): Tweet? {
        val response = apiRequest { tweetService.postLikeTweet(id) }
        return response?.toDomain(context, dataStore.getLoggedInUser().first()?.id)
    }

}