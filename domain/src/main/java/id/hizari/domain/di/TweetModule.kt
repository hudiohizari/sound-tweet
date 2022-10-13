package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.TweetRepository
import id.hizari.domain.usecase.tweet.*

/**
 * Sound Tweet - id.hizari.domain.di
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

@InstallIn(SingletonComponent::class)
@Module
object TweetModule {

    @Provides
    fun provideGetHomeTweetsUseCase(tweetRepository: TweetRepository): GetHomeTweetsUseCase {
        return GetHomeTweetsUseCase(tweetRepository)
    }

    @Provides
    fun provideGetOwnTweetsUseCase(tweetRepository: TweetRepository): GetOwnTweetsUseCase {
        return GetOwnTweetsUseCase(tweetRepository)
    }

    @Provides
    fun provideGetTweetUseCase(tweetRepository: TweetRepository): GetTweetUseCase {
        return GetTweetUseCase(tweetRepository)
    }

    @Provides
    fun providePostTweetUseCase(tweetRepository: TweetRepository): PostTweetUseCase {
        return PostTweetUseCase(tweetRepository)
    }

    @Provides
    fun providePostLikeTweetUseCase(tweetRepository: TweetRepository): PostLikeTweetUseCase {
        return PostLikeTweetUseCase(tweetRepository)
    }

}