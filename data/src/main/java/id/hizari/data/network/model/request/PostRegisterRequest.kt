package id.hizari.data.network.model.request

import com.google.gson.annotations.SerializedName

/**
 * Sound Tweet - id.hizari.data.network.model.request
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class PostRegisterRequest(
    var email: String?,
    @SerializedName("nickname")
    var name: String?,
    var username: String?,
    var password: String?
)