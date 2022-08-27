package com.mattar.nyt_top_stories.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopStories(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    @Json(name = "results")
    val stories: List<Story>,
    val section: String,
    val status: String
)