package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.TweetRepository
import id.hizari.domain.repository.UserRepository
import id.hizari.domain.usecase.TweetUseCase
import id.hizari.domain.usecase.UserUseCase

/**
 * Sound Tweet - id.hizari.domain.di
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideTweetUseCase(tweetRepository: TweetRepository): TweetUseCase {
        return TweetUseCase(tweetRepository)
    }

    @Provides
    fun provideSearchUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }

}