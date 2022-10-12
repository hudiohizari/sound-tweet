package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.NotificationRepository
import id.hizari.domain.usecase.notification.GetNotificationsUseCase

/**
 * Sound Tweet - id.hizari.domain.di
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {

    @Provides
    fun provideGetNotificationsUseCase(
        notificationRepository: NotificationRepository
    ): GetNotificationsUseCase {
        return GetNotificationsUseCase(notificationRepository)
    }

}