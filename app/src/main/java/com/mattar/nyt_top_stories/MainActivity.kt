package com.mattar.nyt_top_stories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.mattar.nyt_top_stories.topstorieslist.TopStoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val articlesTopStoriesListViewModel: TopStoriesListViewModel by viewModels()
        enableEdgeToEdge()
        setContent { NytTheme { NytNavGraph(articlesTopStoriesListViewModel = articlesTopStoriesListViewModel) } }
    }
}