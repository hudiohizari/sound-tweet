package id.hizari.data.network.model.dto

import id.hizari.common.util.Constant
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
    val userFollowing: MutableList<UserDTO?>?,
    val username: String?
): BaseDTO() {

    fun toDomain(loggedInId: Long?): User {
        return User(
            id,
            Constant.URL.IMAGE_USER_PROFILE_EMPTY,
            nickname,
            "@%1s".format(username),
            bio,
            isFollowed(loggedInId),
            userFollowing?.map { it?.username }?.toMutableList()
        )
    }

    private fun isFollowed(loggedInId: Long?): Boolean {
        return userFollower?.find { it?.id == loggedInId } != null
    }

}