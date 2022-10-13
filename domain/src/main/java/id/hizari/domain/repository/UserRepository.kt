package id.hizari.domain.repository

import id.hizari.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Sound Tweet - id.hizari.domain.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

interface UserRepository {

    suspend fun postRegister(
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): User?

    suspend fun postLogin(username: String?, password: String?): User?
    suspend fun getIsLoggedIn(): Boolean
    fun getIsLoggedInLive(): Flow<Boolean>
    suspend fun getLoggedInUser(): User?
    fun getLoggedInUserLive(): Flow<User?>
    suspend fun postLogout()
    suspend fun getSearchUser(query: String?): MutableList<User>?
    suspend fun postFollowUser(userId: Long?): User?

}