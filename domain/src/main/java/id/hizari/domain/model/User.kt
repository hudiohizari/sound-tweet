package id.hizari.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Sound Tweet - id.hizari.domain.model
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@Parcelize
class User(
    val id: Long?,
    val imgUrl: String?,
    val name: String?,
    val userName: String?,
    val bio: String?,
    val joined: String?,
    val isFollowed: Boolean?,
    val follower: String?,
    val following: String?,
    val userFollowingUsername: MutableList<String?>?
): Parcelable