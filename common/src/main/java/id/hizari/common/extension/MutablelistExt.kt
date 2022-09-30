package id.hizari.common.extension

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

fun <T>MutableList<T>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}