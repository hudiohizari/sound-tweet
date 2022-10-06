package id.hizari.domain.usecase

import android.content.Context
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.domain.usecase
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetUseCase(
    private val tweetRepository: TweetRepository
) {

    fun getTweets(context: Context): Flow<Resources<MutableList<Tweet>?>> = flow {
        emit(Resources.Loading())
        try {
            delay(Random.nextLong(500, 5000))
            val response = tweetRepository.getTweets(context)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}