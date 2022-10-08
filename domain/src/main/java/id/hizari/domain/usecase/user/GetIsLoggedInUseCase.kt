package id.hizari.domain.usecase.user

import id.hizari.common.util.Resources
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

class GetIsLoggedInUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resources<Boolean?>> = flow {
        emit(Resources.Loading())
        try {
            emit(Resources.Success(userRepository.getIsLoggedIn()))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}