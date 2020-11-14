package com.mattar.nyt_top_stories.data.source

import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.TopStories

class NytRepositoryImp(private val nytRemoteDataSource: NytDataSource) : NytRepository {
    override suspend fun fetchNytTopStories(): Result<TopStories> =
        nytRemoteDataSource.fetchNytTopStories()
}