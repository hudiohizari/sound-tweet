package id.hizari.data.repository

import id.hizari.data.local.DataStore
import id.hizari.data.network.model.request.PostLoginRequest
import id.hizari.data.network.model.request.PostRegisterRequest
import id.hizari.data.network.model.request.PutEditProfileRequest
import id.hizari.data.network.service.UserService
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
    private val userService: UserService,
    private val dataStore: DataStore
) : UserRepository, SafeApiRequest() {

    override suspend fun postRegister(
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): User? {
        val body = PostRegisterRequest(email, name, username, password)
        val response = apiRequest { userService.postRegister(body) }
        val responseDomain = response?.toDomain(dataStore.getLoggedInUser().first()?.id)
        dataStore.setLoggedInUser(responseDomain)
        return responseDomain
    }

    override suspend fun postLogin(username: String?, password: String?): User? {
        val body = PostLoginRequest(username, password)
        val response = apiRequest { userService.postLogin(body) }
        val responseDomain = response?.toDomain(dataStore.getLoggedInUser().first()?.id)
        dataStore.setLoggedInUser(responseDomain)
        return responseDomain
    }

    override suspend fun getIsLoggedIn(): Boolean {
        return dataStore.getIsLoggedIn().first()
    }

    override fun getIsLoggedInLive(): Flow<Boolean> {
        return dataStore.getIsLoggedIn()
    }

    override suspend fun getLoggedInUser(): User? {
        return dataStore.getLoggedInUser().first()
    }

    override fun getLoggedInUserLive(): Flow<User?> {
        return dataStore.getLoggedInUser()
    }

    override suspend fun postLogout() {
        dataStore.clear()
    }

    override suspend fun getSearchUser(query: String?): MutableList<User>? {
        val response = apiRequest { userService.getSearchUsers(query) }
        return response?.map {
            it.toDomain(dataStore.getLoggedInUser().first()?.id)
        }?.toMutableList()
    }

    override suspend fun postFollowUser(userId: Long?): User? {
        val response = apiRequest { userService.postFollowUser(userId) }
        return response?.toDomain(dataStore.getLoggedInUser().first()?.id)
    }

    override suspend fun putEditProfile(
        bio: String?,
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): User? {
        val loggedInUser = dataStore.getLoggedInUser().first()
        val body = PutEditProfileRequest(bio, email, name, username, password)
        val response = apiRequest { userService.putEditProfile(loggedInUser?.id, body) }
        return response?.toDomain(loggedInUser?.id).also {
            dataStore.setLoggedInUser(it)
        }
    }

}