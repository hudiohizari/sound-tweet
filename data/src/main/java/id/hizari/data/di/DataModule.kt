package id.hizari.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.data.local.DataStore
import id.hizari.data.network.service.SoundTweetService
import id.hizari.data.repository.TweetRepositoryImpl
import id.hizari.data.repository.UserRepositoryImpl
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
    fun provideTweetRepository(): TweetRepository {
        return TweetRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        soundTweetService: SoundTweetService,
        dataStore: DataStore
    ): UserRepository {
        return UserRepositoryImpl(soundTweetService, dataStore)
    }

}