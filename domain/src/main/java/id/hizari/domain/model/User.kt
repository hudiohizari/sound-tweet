package id.hizari.domain.model

/**
 * Sound Tweet - id.hizari.domain.model
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class User(
    val id: Int?,
    val name: String?,
    val userName: String?,
    val bio: String?,
    val userFollower: MutableList<User?>?
) {

    fun isFollowed(): Boolean {
        return userFollower?.find { it?.id == id } != null
    }

}