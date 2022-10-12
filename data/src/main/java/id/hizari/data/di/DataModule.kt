package id.hizari.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.data.local.DataStore
import id.hizari.data.network.service.FileStackService
import id.hizari.data.network.service.TweetService
import id.hizari.data.network.service.UserService
import id.hizari.data.repository.FileStackRepositoryImpl
import id.hizari.data.repository.NotificationRepositoryImpl
import id.hizari.data.repository.TweetRepositoryImpl
import id.hizari.data.repository.UserRepositoryImpl
import id.hizari.domain.repository.FileStackRepository
import id.hizari.domain.repository.NotificationRepository
import id.hizari.domain.repository.TweetRepository
import id.hizari.domain.repository.UserRepository
import javax.inject.Singleton

/**
 * Sound Tweet - id.hizari.data.di
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        dataStore: DataStore
    ): UserRepository {
        return UserRepositoryImpl(userService, dataStore)
    }

    @Provides
    @Singleton
    fun provideTweetRepository(
        tweetService: TweetService,
        dataStore: DataStore
    ): TweetRepository {
        return TweetRepositoryImpl(tweetService, dataStore)
    }

    @Provides
    @Singleton
    fun provideFileStackRepository(fileStackService: FileStackService): FileStackRepository {
        return FileStackRepositoryImpl(fileStackService)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepositoryImpl()
    }

}