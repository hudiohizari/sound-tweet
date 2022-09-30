package id.hizari.data.network.model.base

/**
 * Sound Tweet - id.hizari.data.network.model.base
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

abstract class BaseDTO(
    val error: MutableList<Error>? = null
) {
    data class Error(
        val code: String?,
        val field: String?,
        val message: String?
    )

}