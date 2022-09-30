package id.hizari.common.util

import androidx.annotation.Keep

/**
 * Sound Tweet - id.hizari.data.mapper
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

@Keep
sealed class Resources<T>(val data: T?= null, val throwable: Throwable?= null) {

    @Keep
    class Success<T>(data: T): Resources<T>(data)
    
    @Keep
    class Empty<T>: Resources<T>()

    @Keep
    class Loading<T>: Resources<T>()

    @Keep
    class Error<T>(throwable: Throwable): Resources<T>(throwable = throwable)

    fun isLoading(): Boolean {
        return data is Loading<*>
    }

    fun isFailed(): Boolean {
        return data is Error<*>
    }

}
