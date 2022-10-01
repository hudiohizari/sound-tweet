package id.hizari.data.repository

import id.hizari.data.mapper.toDomain
import id.hizari.data.network.service.SoundTweetService
import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetRepositoryImpl @Inject constructor(
    private val soundTweetService: SoundTweetService
): TweetRepository, SafeApiRequest() {

    override suspend fun getTweets(): MutableList<Tweet>? {
        val response = apiRequest { soundTweetService.getTweets() }
        return response?.map {
            it.toDomain()
        }?.toMutableList()
    }

}