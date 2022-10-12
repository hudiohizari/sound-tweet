package id.hizari.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hizari.data.network.service.FileStackService
import id.hizari.data.network.service.TweetService
import id.hizari.data.network.service.UserService
import id.hizari.data.network.util.AuthInterceptor
import id.hizari.data.network.util.Client
import javax.inject.Singleton

/**
 * Sound Tweet - id.hizari.data.di
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
    fun provideClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): Client {
        return Client(authInterceptor, chuckerInterceptor)
    }

    @Provides
    @Singleton
    fun provideUserService(client: Client): UserService {
        return UserService.invoke(client)
    }

    @Provides
    @Singleton
    fun provideTweetService(client: Client): TweetService {
        return TweetService.invoke(client)
    }

    @Provides
    @Singleton
    fun provideFileStackService(client: Client): FileStackService {
        return FileStackService.invoke(client)
    }

}