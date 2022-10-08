package id.hizari.domain.usecase.user

import id.hizari.domain.repository.UserRepository

/**
 * Sound Tweet - id.hizari.domain.usecase.user
 *
 * Created by Hudio Hizari on 08/10/2022.
 * https://github.com/hudiohizari
 *
 */

class GetIsLoggedInLiveUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.getIsLoggedInLive()

}