package id.hizari.data.network.model.request

/**
 * Sound Tweet - id.hizari.data.network.model.request
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class RegisterRequest(
    var email: String?,
    var name: String?,
    var username: String?,
    var password: String?
)