package id.hizari.domain.repository

import id.hizari.domain.model.User

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
    suspend fun searchUser(query: String?): MutableList<User>

}