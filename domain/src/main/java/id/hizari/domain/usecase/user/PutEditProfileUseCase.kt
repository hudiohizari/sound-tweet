package id.hizari.domain.usecase.user

import id.hizari.common.util.Resources
import id.hizari.domain.model.User
import id.hizari.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Sound Tweet - id.hizari.domain.usecase.user
 *
 * Created by Hudio Hizari on 08/10/2022.
 * https://github.com/hudiohizari
 *
 */


class PutEditProfileUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(
        bio: String?,
        email: String?,
        name: String?,
        username: String?,
        password: String?
    ): Flow<Resources<User?>> = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.putEditProfile(bio, email, name, username, password)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}