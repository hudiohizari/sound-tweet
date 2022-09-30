package id.hizari.data.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hizari.data.network.service.SoundTweetService
import id.hizari.data.network.util.Client
import id.hizari.data.repository.TweetRepositoryImpl
import id.hizari.data.repository.UserRepositoryImpl
import id.hizari.domain.repository.TweetRepository
import id.hizari.domain.repository.UserRepository
import retrofit2.Retrofit
import javax.inject.Singleton

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
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext
        context: Context
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideClient(chuckerInterceptor: ChuckerInterceptor): Client {
        return Client(chuckerInterceptor)
    }

    @Provides
    @Singleton
    fun provideSoundTweeService(client: Client): SoundTweetService {
        return SoundTweetService.invoke(client)
    }

    @Provides
    @Singleton
    fun provideTweetRepository(): TweetRepository {
        return TweetRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(soundTweetService: SoundTweetService): UserRepository {
        return UserRepositoryImpl(soundTweetService)
    }

}