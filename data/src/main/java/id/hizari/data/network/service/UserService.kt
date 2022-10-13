package id.hizari.data.network.service

import id.hizari.data.network.util.Client
import id.hizari.common.util.Constant
import id.hizari.data.network.model.dto.UserDTO
import id.hizari.data.network.model.dto.UsersDTO
import id.hizari.data.network.model.request.PostLoginRequest
import id.hizari.data.network.model.request.PostRegisterRequest
import id.hizari.data.network.model.request.PutEditProfileRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Sound Tweet - id.hizari.data.network.service
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface UserService {

    @POST(".")
    suspend fun postRegister(
        @Body body: PostRegisterRequest
    ): Response<UserDTO>

    @POST("login")
    suspend fun postLogin(
        @Body body: PostLoginRequest
    ): Response<UserDTO>

    @GET("search")
    suspend fun getSearchUsers(
        @Query("keyword") query: String?
    ): Response<UsersDTO>

    @POST("follow/{id}")
    suspend fun postFollowUser(
        @Path("id") userId: Long?
    ): Response<UserDTO>

    @PUT("{id}")
    suspend fun putEditProfile(
        @Path("id") userId: Long?,
        @Body body: PutEditProfileRequest
    ): Response<UserDTO>

    companion object {
        operator fun invoke(client: Client): UserService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL.BASE_URL_USERS)
                .client(client.provideClient())
                .build()
                .create(UserService::class.java)
        }
    }

}