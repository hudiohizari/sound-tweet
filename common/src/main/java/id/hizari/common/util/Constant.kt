package id.hizari.common.util

import id.hizari.common.BuildConfig

/**
 * Sound Tweet - id.hizari.common.util
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

object Constant {

    object URL {
        const val BASE_URL = BuildConfig.BASE_URL
        const val BASE_URL_USERS = BASE_URL + BuildConfig.USERS_PATH
        const val BASE_URL_TWEET = BASE_URL + BuildConfig.TWEET_PATH
        const val FILE_STACK_BASE_URL = BuildConfig.FILE_STACK_BASE_URL
        const val IMAGE_INVALID = "https://via.placeholder.com/150?text=No+Image"
    }

    object Key {
        const val FILE_STACK_KEY = BuildConfig.FILE_STACK_KEY
    }

}