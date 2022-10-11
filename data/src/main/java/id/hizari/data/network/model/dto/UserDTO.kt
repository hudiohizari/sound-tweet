package id.hizari.data.network.model.dto

import id.hizari.data.network.model.base.BaseDTO
import id.hizari.domain.model.User

/**
 * Sound Tweet - id.hizari.data.network.model.dto
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class UserDTO(
    val bio: String?,
    val createdAt: String?,
    val email: String?,
    val followers: Int?,
    val following: Int?,
    val id: Long?,
    val nickname: String?,
    val password: String?,
    val updatedAt: String?,
    val userFollower: MutableList<UserDTO?>?,
    val username: String?
): BaseDTO() {

    fun toDomain(loggedInId: Long?): User {
        return User(
            id,
            nickname,
            username,
            bio,
            isFollowed(loggedInId)
        )
    }

    private fun isFollowed(loggedInId: Long?): Boolean {
        return userFollower?.find { it?.id == loggedInId } != null
    }

}