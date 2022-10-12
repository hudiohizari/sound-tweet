package id.hizari.domain.usecase.notification

import id.hizari.common.util.Resources
import id.hizari.domain.model.Notification
import id.hizari.domain.repository.NotificationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.domain.usecase.tweet
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

class GetNotificationsUseCase(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(): Flow<Resources<MutableList<Notification>?>> = flow {
        emit(Resources.Loading())
        try {
            val response = notificationRepository.getNotifications()
            delay(Random.nextLong(0, 1000))
            emit(Resources.Success(response))
            if (Random.nextInt(0, 5) == 3) {
                emit(Resources.Error(Throwable("Something Error")))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }

}