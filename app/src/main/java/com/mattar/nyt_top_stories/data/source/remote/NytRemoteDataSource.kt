package com.mattar.nyt_top_stories.data.source.remote

import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.TopStories
import com.mattar.nyt_top_stories.data.retrofit.NytService
import com.mattar.nyt_top_stories.data.source.NytDataSource

class NytRemoteDataSource(private val nytService: NytService) : NytDataSource {
    override suspend fun fetchNytTopStories(): Result<TopStories> {
        return try {
            Result.Success(nytService.fetchNytTopStories())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}