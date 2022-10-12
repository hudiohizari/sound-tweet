package id.hizari.domain.repository

import id.hizari.domain.model.Notification

/**
 * Sound Tweet - id.hizari.domain.repository
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface NotificationRepository {

    fun getNotifications(): MutableList<Notification>?

}