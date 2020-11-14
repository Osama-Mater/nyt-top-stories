package com.mattar.nyt_top_stories.di

import com.mattar.nyt_top_stories.data.retrofit.NytService
import com.mattar.nyt_top_stories.data.source.NytDataSource
import com.mattar.nyt_top_stories.data.source.NytRepository
import com.mattar.nyt_top_stories.data.source.NytRepositoryImp
import com.mattar.nyt_top_stories.data.source.remote.NytRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteNytDataSource

    @Singleton
    @RemoteNytDataSource
    @Provides
    fun provideTvShowRemoteDataSource(nytService: NytService): NytDataSource {
        return NytRemoteDataSource(nytService)
    }
}

/**
 * The binding for TvShowRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(ApplicationComponent::class)
object NytRepositoryModule {

    @Singleton
    @Provides
    fun provideNytRepository(
        @DataModule.RemoteNytDataSource nytRemoteDataSource: NytDataSource
    ): NytRepository {
        return NytRepositoryImp(
            nytRemoteDataSource
        )
    }
}
