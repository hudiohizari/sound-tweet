package id.hizari.domain.usecase.tweet

import android.content.Context
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Sound Tweet - id.hizari.domain.usecase.tweet
 *
 * Created by Hudio Hizari on 14/10/2022.
 * https://github.com/hudiohizari
 *
 */

class PostReplyTweetUseCase(
    private val tweetRepository: TweetRepository
) {

    operator fun invoke(
        context: Context,
        id: Long?,
        caption: String?,
        postUrl: String?,
        text: String?
    ): Flow<Resources<Tweet?>> = flow {
        emit(Resources.Loading())
        try {
            val response = tweetRepository.postReplyTweet(context, id, caption, postUrl, text)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}