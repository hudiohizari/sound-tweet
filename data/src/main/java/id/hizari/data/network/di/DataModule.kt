package id.hizari.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.data.repository.TweetRepositoryImpl
import id.hizari.domain.repository.TweetRepository

/**
 * Sound Tweet - id.hizari.data.network.di
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideTweetRepository(): TweetRepository {
        return TweetRepositoryImpl()
    }

}