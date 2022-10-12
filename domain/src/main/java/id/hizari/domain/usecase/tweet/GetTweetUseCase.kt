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
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

class GetTweetUseCase(
    private val tweetRepository: TweetRepository
) {

    operator fun invoke(context: Context, id: Long?): Flow<Resources<Tweet?>> = flow {
        emit(Resources.Loading())
        try {
            val response = tweetRepository.getTweet(context, id)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}