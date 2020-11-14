package com.mattar.nyt_top_stories.data.source

import com.mattar.nyt_top_stories.data.Result
import com.mattar.nyt_top_stories.data.model.TopStories

interface NytDataSource {
    suspend fun fetchNytTopStories(): Result<TopStories>
}