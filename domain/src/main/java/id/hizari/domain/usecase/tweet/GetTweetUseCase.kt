package id.hizari.domain.usecase.tweet

import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Sound Tweet - id.hizari.domain.usecase
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

class GetTweetUseCase(
    private val tweetRepository: TweetRepository
) {

    operator fun invoke(): Flow<Resources<MutableList<Tweet>>> = flow {
        emit(Resources.Loading())
        try {
            val response = tweetRepository.getTweets()
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}