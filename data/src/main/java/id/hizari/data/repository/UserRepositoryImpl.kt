package id.hizari.data.repository

import id.hizari.data.local.DataStore
import id.hizari.data.mapper.toDomain
import id.hizari.data.network.model.request.LoginRequest
import id.hizari.data.network.model.request.RegisterRequest
import id.hizari.data.network.service.SoundTweetService
import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.User
import id.hizari.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class UserRepositoryImpl @Inject constructor(
    private val soundTweetService: SoundTweetService,
    private val dataStore: DataStore
) : UserRepository, SafeApiRequest() {

    override suspend fun postRegister(
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): User? {
        val body = RegisterRequest(email, name, username, password)
        val response = apiRequest { soundTweetService.postRegister(body) }
        dataStore.setLoggedInUsername(response?.username)
        dataStore.setIsLoggedIn(true)
        return response?.toDomain()
    }

    override suspend fun postLogin(username: String?, password: String?): User? {
        val body = LoginRequest(username, password)
        val response = apiRequest { soundTweetService.postLogin(body) }
        dataStore.setLoggedInUsername(response?.username)
        dataStore.setIsLoggedIn(true)
        return response?.toDomain()
    }

    override suspend fun getIsLoggedIn(): Boolean {
        return dataStore.getIsLoggedIn().first()
    }

    override fun getIsLoggedInLive(): Flow<Boolean> {
        return dataStore.getIsLoggedIn()
    }

    override suspend fun searchUser(query: String?): MutableList<User> {
        return mutableListOf(
            User(
                0,
                "Martha Craig",
                "@craig_love",
                "Random",
                mutableListOf()
            ),
            User(
                1,
                "Joshua Brown",
                "@jbrown",
                "Random",
                mutableListOf()
            ),
            User(
                2,
                "Komol Kuchkarov",
                "@kkuchkarov",
                "Random",
                mutableListOf()
            ),
            User(
                3,
                "Adam West",
                "@awest",
                "Random",
                mutableListOf()
            )
        ).filter { it.name?.lowercase()?.contains(query?.lowercase() ?: "") == true }
            .toMutableList()
    }

}