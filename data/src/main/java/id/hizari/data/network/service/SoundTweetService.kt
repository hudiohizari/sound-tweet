package id.hizari.data.network.service

import id.hizari.data.network.util.Client
import id.hizari.common.util.Constant
import id.hizari.data.network.model.dto.UserDTO
import id.hizari.data.network.model.request.LoginRequest
import id.hizari.data.network.model.request.RegisterRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Sound Tweet - id.hizari.data.network.service
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface SoundTweetService {

    @POST("users")
    suspend fun postRegister(
        @Body body: RegisterRequest
    ): Response<UserDTO>

    @POST("users/login")
    suspend fun postLogin(
        @Body body: LoginRequest
    ): Response<UserDTO>

    companion object {
        operator fun invoke(client: Client): SoundTweetService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(client.provideClient())
                .build()
                .create(SoundTweetService::class.java)
        }
    }

}