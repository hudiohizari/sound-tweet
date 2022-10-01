package id.hizari.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hizari.data.local.DataStore
import id.hizari.data.repository.TweetRepositoryImpl
import id.hizari.domain.repository.TweetRepository
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
object LocalModule {

    @Provides
    @Singleton
    fun getDataStore(
        @ApplicationContext
        context: Context
    ): DataStore {
        return DataStore(context)
    }

}