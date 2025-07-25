package com.mattar.nyt_top_stories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattar.nyt_top_stories.topstorieslist.ArticlesTopStoriesScreen

enum class NytDestinations(
    val route: String,
    val label: String,
    val icon: Int,
    val contentDescription: String
) {
    TOPSTORIES(
        "ArticlesTopStoriesScreen",
        "Top Stories",
        R.drawable.ic_round_dashboard,
        "Top Stories"
    ),
    FAVOURITES("Favorites", "Favorites", R.drawable.ic_round_favorite, "Favorites"),
}

@Composable
fun NytNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: NytDestinations,
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController, startDestination = startDestination.route) {
        NytDestinations.entries.forEach { destination ->
            composable(route = destination.route) {
                when (destination) {
                    NytDestinations.TOPSTORIES -> ArticlesTopStoriesScreen()
                    NytDestinations.FAVOURITES -> {}
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    val navController: NavHostController = rememberNavController()

    val startDestination = NytDestinations.TOPSTORIES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ), title = { Text("Top Stories") }
            )
        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                NytDestinations.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }

                    )
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            NytNavGraph(
                navController,
                startDestination,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}