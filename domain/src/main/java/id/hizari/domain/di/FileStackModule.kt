package id.hizari.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hizari.domain.repository.FileStackRepository
import id.hizari.domain.usecase.filestack.PostFileUseCase

/**
 * Sound Tweet - id.hizari.domain.di
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

@InstallIn(SingletonComponent::class)
@Module
object FileStackModule {

    @Provides
    fun providePostFileUseCase(fileStackRepository: FileStackRepository): PostFileUseCase {
        return PostFileUseCase(fileStackRepository)
    }

}