package com.mattar.nyt_top_stories.data.model

import com.google.gson.annotations.SerializedName

data class TopStories(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    @SerializedName("results")
    val stories: List<Story>,
    val section: String,
    val status: String
)