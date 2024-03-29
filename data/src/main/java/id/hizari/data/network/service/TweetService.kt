package id.hizari.data.network.service

import id.hizari.common.util.Constant
import id.hizari.data.network.model.dto.TweetDTO
import id.hizari.data.network.model.dto.TweetsDTO
import id.hizari.data.network.model.request.PostTweetRequest
import id.hizari.data.network.util.Client
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Sound Tweet - id.hizari.data.network.service
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface TweetService {

    @GET(".")
    suspend fun getTweets(): Response<TweetsDTO>

    @GET("user/{username}")
    suspend fun getUserTweets(
        @Path("username") username: String?
    ): Response<TweetsDTO>

    @GET("{id}")
    suspend fun getTweet(
        @Path("id") id: Long?
    ): Response<TweetDTO>

    @POST(".")
    suspend fun postTweet(
        @Body body: PostTweetRequest
    ): Response<TweetDTO>

    @POST("reply/{id}")
    suspend fun postReplyTweet(
        @Path("id") id: Long?,
        @Body body: PostTweetRequest
    ): Response<TweetDTO>

    @POST("like/{id}")
    suspend fun postLikeTweet(
        @Path("id") id: Long?
    ): Response<TweetDTO>

    companion object {
        operator fun invoke(client: Client): TweetService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL.BASE_URL_TWEET)
                .client(client.provideClient())
                .build()
                .create(TweetService::class.java)
        }
    }

}