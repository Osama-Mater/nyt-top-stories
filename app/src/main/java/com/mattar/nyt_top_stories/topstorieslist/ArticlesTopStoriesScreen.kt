package com.mattar.nyt_top_stories.topstorieslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.utils.dateString

@Composable
fun ArticlesTopStoriesScreen(
    viewModel: TopStoriesListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {}) { paddingValues ->
        val uiState by viewModel.viewStateFlow.collectAsStateWithLifecycle()
        ArticlesStoryList(topStories = uiState.topStories, onStoryClick = {})
        viewModel.loadData()
    }
}

@Composable
private fun ArticlesStoryList(topStories: List<Story>, onStoryClick: (Story) -> Unit) {
    LazyColumn {
        items(topStories) { story ->
            ArticleStoryItem(
                articleStory = story,
                onStoryClick = onStoryClick
            )
        }
    }
}

@Composable
private fun ArticleStoryItem(
    articleStory: Story,
    onStoryClick: (Story) -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .height(194.dp),
                contentScale = ContentScale.FillBounds,
                model = articleStory.multimedia?.first { image -> image.format == "Large Thumbnail" }?.url,
                contentDescription = R.string.content_description_media.toString()
            )
            Text(
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                text = articleStory.title
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = articleStory.byline
            )
            Text(
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = dateString(articleStory.published_date)
            )
        }
    }
}