package com.mattar.nyt_top_stories

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattar.nyt_top_stories.topstorieslist.ArticlesTopStoriesScreen

object NytDestinations {
    const val TOP_STORIES_ROUTE = "topStories"
}

@Composable
fun NytNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NytDestinations.TOP_STORIES_ROUTE
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NytDestinations.TOP_STORIES_ROUTE) {
            ArticlesTopStoriesScreen()
        }
    }
}