package com.mattar.nyt_top_stories.topstorieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mattar.nyt_top_stories.R
import com.mattar.nyt_top_stories.data.model.Story
import com.mattar.nyt_top_stories.utils.dateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesTopStoriesScreen(
    onStoryClick: () -> Unit,
    viewModel: TopStoriesListViewModel,//= hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ), title = { Text("Top Stories") }
            )
        }) { paddingValues ->
        val uiState by viewModel.viewStateFlow.collectAsStateWithLifecycle()
        ArticlesStoryList(
            paddingValues,
            topStories = uiState.topStories,
            viewModel = viewModel,
            onStoryClick = onStoryClick
        )
        viewModel.loadData()
    }
}

@Composable
private fun ArticlesStoryList(
    padding: PaddingValues,
    topStories: List<Story>,
    viewModel: TopStoriesListViewModel,
    onStoryClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(topStories) { story ->
            ArticleStoryItem(
                articleStory = story,
                viewModel = viewModel,
                onStoryClick = onStoryClick
            )
        }
    }
}

@Composable
private fun ArticleStoryItem(
    articleStory: Story,
    viewModel: TopStoriesListViewModel,
    onStoryClick: () -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                viewModel.selectStory(story = articleStory)
                onStoryClick()
            },
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