package com.mattar.nyt_top_stories.data.retrofit

import com.mattar.nyt_top_stories.data.model.TopStories
import retrofit2.http.GET

interface NytService {
    @GET("/svc/topstories/v2/home.json")
    suspend fun fetchNytTopStories(): TopStories
}