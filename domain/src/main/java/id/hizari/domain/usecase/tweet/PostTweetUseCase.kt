package id.hizari.domain.usecase.tweet

import android.content.Context
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.domain.usecase.tweet
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

class PostTweetUseCase(
    private val tweetRepository: TweetRepository
) {

    operator fun invoke(
        context: Context,
        caption: String?,
        postUrl: String?,
        text: String?
    ): Flow<Resources<Tweet?>> = flow {
        emit(Resources.Loading())
        try {
            val response = tweetRepository.postTweet(context, caption, postUrl, text)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}