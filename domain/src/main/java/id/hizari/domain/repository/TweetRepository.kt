package id.hizari.domain.repository

import id.hizari.domain.model.Tweet

/**
 * Sound Tweet - id.hizari.domain.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

interface TweetRepository {

    suspend fun getTweets(): MutableList<Tweet>

}