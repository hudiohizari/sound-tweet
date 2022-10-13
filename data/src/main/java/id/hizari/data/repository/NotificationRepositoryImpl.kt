package id.hizari.data.repository

import id.hizari.common.util.Constant
import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.Notification
import id.hizari.domain.model.User
import id.hizari.domain.repository.NotificationRepository
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

class NotificationRepositoryImpl @Inject constructor(): NotificationRepository, SafeApiRequest() {

    override fun getNotifications(): MutableList<Notification>? {
        return mutableListOf<Notification>().apply {
            add(Notification(
                1,
                "Sharing Session: Creating a perfect mayonaise!",
                User(
                    1,
                    Constant.URL.IMAGE_USER_PROFILE_EMPTY,
                    "Saad Drusteer",
                    "sdrusterr",
                    "",
                    "sdrusterr@gmail.com",
                    "sdrusterr",
                    "April 2022",
                    false,
                    "0",
                    "0",
                    mutableListOf()
                )
            ))
            add(Notification(
                2,
                "Having issue with running a business",
                User(
                    2,
                    Constant.URL.IMAGE_USER_PROFILE_EMPTY,
                    "UX Upper",
                    "uupper",
                    "",
                    "uupper@gmail.com",
                    "uupper",
                    "September 2022",
                    false,
                    "0",
                    "0",
                    mutableListOf()
                )
            ))
            add(Notification(
                3,
                "Having issue with running a business",
                User(
                    3,
                    Constant.URL.IMAGE_USER_PROFILE_EMPTY,
                    "Teador Van Schneider",
                    "tvschneider",
                    "",
                    "tvschneider@gmail.com",
                    "tvschneider",
                    "April 2022",
                    false,
                    "0",
                    "0",
                    mutableListOf()
                )
            ))
        }
    }

}