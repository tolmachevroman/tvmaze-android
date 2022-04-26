package com.tv.maze.main.widgets

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import com.tv.maze.data.models.ShowType
import com.tv.maze.main.viewmodels.MainViewModel
import com.tv.maze.utils.Resource
import com.tv.maze.utils.SeasonsMocks

enum class Route(val value: String) {
    SHOW_LIST_SCREEN("shows"),
    SHOW_DETAILS_SCREEN("shows/{show}"),
    EPISODE_DETAILS_SCREEN("episodes/{episode}"),
    FAVORITE_SHOWS_SCREEN("shows/favorite"),
    PERSON_DETAILS_SCREEN("people/{person}")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigation(
    viewModel: MainViewModel
//    shows: Resource<ArrayList<Show>>,
//    seasonsByShow: Resource<ArrayList<Season>>,
//    onShowClick: (Int) -> Unit,
//    onQueryChange: (String) -> Unit
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Route.SHOW_LIST_SCREEN.value) {
        composable(
            Route.SHOW_LIST_SCREEN.value,
            enterTransition = {
                routeEnterTransition()
            },
            exitTransition = {
                routeExitTransition()
            }
        ) {
            ShowListScreen(
                shows = viewModel.shows,
                onQueryChange = { newQuery ->
                    viewModel.onQueryChange(newQuery)
                },
                onShowClick = { show ->
                    viewModel.getSeasonsByShow(show.id)
                    val json = Uri.encode(Gson().toJson(show))
                    navController.navigate(Route.SHOW_DETAILS_SCREEN.value.replace("{show}", json))
                }
            )
        }
        composable(
            Route.SHOW_DETAILS_SCREEN.value,
            arguments = listOf(navArgument("show") { type = ShowType() }),
            enterTransition = {
                routeEnterTransition()
            },
            exitTransition = {
                routeExitTransition()
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Show>("show")?.let { show ->
                ShowDetailsScreen(
                    show = show,
                    seasonsByShow = viewModel.seasonsByShow,
                    onEpisodeClick = {
                        navController.navigate(Route.EPISODE_DETAILS_SCREEN.value) //TODO pass param
                    }
                )
            }
        }
    }
}

private fun routeEnterTransition(): EnterTransition =
    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))

private fun routeExitTransition(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))