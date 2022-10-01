package id.hizari.domain.usecase

import id.hizari.common.util.Resources
import id.hizari.domain.model.User
import id.hizari.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Sound Tweet - id.hizari.domain.usecase
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class UserUseCase(
    private val userRepository: UserRepository
) {

    fun postRegister(
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): Flow<Resources<User?>> = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.postRegister(email, name, username, password)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    fun postLogin(username: String?, password: String?): Flow<Resources<User?>> = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.postLogin(username, password)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    fun isLoggedIn(): Flow<Resources<Boolean?>> = flow {
        emit(Resources.Loading())
        try {
            emit(Resources.Success(userRepository.getIsLoggedIn()))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    fun isLoggedInLive() = userRepository.getIsLoggedInLive()

    fun searchUser(query: String?): Flow<Resources<MutableList<User>?>> = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.searchUser(query)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

    fun followUser(userId: Int?): Flow<Resources<User?>> = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.followUser(userId)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}