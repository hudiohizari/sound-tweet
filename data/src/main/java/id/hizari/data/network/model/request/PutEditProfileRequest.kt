package id.hizari.data.network.model.request

import com.google.gson.annotations.SerializedName

/**
 * Sound Tweet - id.hizari.data.network.model.request
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class PutEditProfileRequest(
    var bio: String?,
    var email: String?,
    @SerializedName("nickname")
    var name: String?,
    var username: String?,
    var password: String?
)