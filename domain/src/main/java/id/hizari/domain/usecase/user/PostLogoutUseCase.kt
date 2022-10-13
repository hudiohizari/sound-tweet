package id.hizari.domain.usecase.user

import id.hizari.common.util.Resources
import id.hizari.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

/**
 * Sound Tweet - id.hizari.domain.usecase.user
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */


class PostLogoutUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke() = flow {
        emit(Resources.Loading())
        try {
            val response = userRepository.postLogout()
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}