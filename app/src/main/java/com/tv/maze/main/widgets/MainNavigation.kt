package com.tv.maze.main.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tv.maze.utils.SeasonsMocks

enum class Route {
    SHOW_LIST_SCREEN,
    SHOW_DETAILS_SCREEN,
    EPISODE_DETAILS_SCREEN,
    FAVORITE_SHOWS_SCREEN,
    PERSON_DETAILS_SCREEN
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    onQueryChange: (String) -> Unit
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Route.SHOW_LIST_SCREEN.name) {
        composable(
            Route.SHOW_LIST_SCREEN.name,
            enterTransition = {
                routeEnterTransition()
            },
            exitTransition = {
                routeExitTransition()
            }
        ) {
            ShowListScreen(
                onQueryChange = onQueryChange,
                onShowClick = {
                    navController.navigate(Route.SHOW_DETAILS_SCREEN.name) //TODO pass param
                }
            )
        }
        composable(
            Route.SHOW_DETAILS_SCREEN.name,
            enterTransition = {
                routeEnterTransition()
            },
            exitTransition = {
                routeExitTransition()
            }
        ) {
            //TODO pass real values
            ShowDetailsScreen(
                posterUrl = "https://es.web.img3.acsta.net/pictures/16/11/23/19/25/592195.png",
                name = "Black Mirror",
                days = arrayListOf("Wednesday, Thursday"),
                time = "22:00",
                genres = arrayListOf("Drama, Thriller"),
                summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
                seasons = SeasonsMocks.seasons,
                onEpisodeClick = {
                    navController.navigate(Route.EPISODE_DETAILS_SCREEN.name) //TODO pass param
                }
            )
        }
    }
}

private fun routeEnterTransition(): EnterTransition =
    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))

private fun routeExitTransition(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))