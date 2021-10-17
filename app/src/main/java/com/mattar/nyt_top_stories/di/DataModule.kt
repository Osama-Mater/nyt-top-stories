package com.mattar.nyt_top_stories.di

import com.mattar.nyt_top_stories.data.retrofit.NytService
import com.mattar.nyt_top_stories.data.source.NytDataSource
import com.mattar.nyt_top_stories.data.source.NytRepository
import com.mattar.nyt_top_stories.data.source.NytRepositoryImp
import com.mattar.nyt_top_stories.data.source.remote.NytRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteNytDataSource

    @RemoteNytDataSource
    @Provides
    fun provideNytRemoteDataSource(nytService: NytService): NytDataSource {
        return NytRemoteDataSource(nytService)
    }
}

/**
 * The binding for NytRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(ViewModelComponent::class)
object NytRepositoryModule {

    @Provides
    fun provideNytRepository(
        @DataModule.RemoteNytDataSource nytRemoteDataSource: NytDataSource
    ): NytRepository {
        return NytRepositoryImp(
            nytRemoteDataSource
        )
    }
}
