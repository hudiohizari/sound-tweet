package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.TweetRepository
import id.hizari.domain.repository.UserRepository
import id.hizari.domain.usecase.tweet.GetTweetsUseCase
import id.hizari.domain.usecase.user.SearchUserUseCase

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
    fun provideTweetUseCase(tweetRepository: TweetRepository): GetTweetsUseCase {
        return GetTweetsUseCase(tweetRepository)
    }

    @Provides
    fun provideSearchUserUseCase(userRepository: UserRepository): SearchUserUseCase {
        return SearchUserUseCase(userRepository)
    }

}