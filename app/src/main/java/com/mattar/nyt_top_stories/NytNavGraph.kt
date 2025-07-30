package com.mattar.nyt_top_stories

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mattar.nyt_top_stories.NytScreens.STORY_DETAILS
import com.mattar.nyt_top_stories.storydetails.StoryDetailsScreen
import com.mattar.nyt_top_stories.topstorieslist.ArticlesTopStoriesScreen
import com.mattar.nyt_top_stories.topstorieslist.TopStoriesListViewModel

object NytScreens {
    const val TOP_STORIES = "topStories"
    const val FAVOURITES = "favourites"
    const val STORY_DETAILS = "storyDetails"
}

data class NavigationItem(val route: String, val label: String, val icon: Int)

@Composable
fun NytNavGraph(
    navController: NavHostController = rememberNavController(),
    articlesTopStoriesListViewModel: TopStoriesListViewModel
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Conditionally display the BottomNavigation
            if (currentRoute == NytScreens.TOP_STORIES || currentRoute == NytScreens.FAVOURITES) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NytScreens.TOP_STORIES,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NytScreens.TOP_STORIES) {
                ArticlesTopStoriesScreen(
                    viewModel = articlesTopStoriesListViewModel,
                    onStoryClick = {
                        navController.navigate("$STORY_DETAILS")
                    })
            }
            composable(NytScreens.FAVOURITES) {
            }
            composable(route = "$STORY_DETAILS") { backStackEntry ->
                StoryDetailsScreen(
                    articlesTopStoriesListViewModel = articlesTopStoriesListViewModel,
                    onBack = {
                        navController.popBackStack()
                    })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val items = listOf(
        NavigationItem(NytScreens.TOP_STORIES, "Top Stories", R.drawable.ic_round_dashboard),
        NavigationItem(NytScreens.FAVOURITES, "Favorites", R.drawable.ic_round_favorite)
    )

    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(screen.icon), contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}