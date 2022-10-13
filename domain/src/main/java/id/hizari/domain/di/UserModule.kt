package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.UserRepository
import id.hizari.domain.usecase.user.*

/**
 * Sound Tweet - id.hizari.domain.di
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@InstallIn(SingletonComponent::class)
@Module
object UserModule {

    @Provides
    fun providePostRegisterUseCase(userRepository: UserRepository): PostRegisterUseCase {
        return PostRegisterUseCase(userRepository)
    }

    @Provides
    fun providePostLoginUseCase(userRepository: UserRepository): PostLoginUseCase {
        return PostLoginUseCase(userRepository)
    }

    @Provides
    fun provideGetIsLoggedInUseCase(userRepository: UserRepository): GetIsLoggedInUseCase {
        return GetIsLoggedInUseCase(userRepository)
    }

    @Provides
    fun provideGetIsLoggedInLiveUseCase(userRepository: UserRepository): GetIsLoggedInLiveUseCase {
        return GetIsLoggedInLiveUseCase(userRepository)
    }

    @Provides
    fun provideGetLoggedInUserLiveUseCase(userRepository: UserRepository): GetLoggedInUserLiveUseCase {
        return GetLoggedInUserLiveUseCase(userRepository)
    }

    @Provides
    fun providePostLogoutUseCase(userRepository: UserRepository): PostLogoutUseCase {
        return PostLogoutUseCase(userRepository)
    }

    @Provides
    fun provideGetSearchUserUseCase(userRepository: UserRepository): GetSearchUserUseCase {
        return GetSearchUserUseCase(userRepository)
    }

    @Provides
    fun providePostFollowUserUseCase(userRepository: UserRepository): PostFollowUserUseCase {
        return PostFollowUserUseCase(userRepository)
    }

}