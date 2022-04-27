package com.tv.maze.main.widgets

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.Show
import com.tv.maze.data.models.navigation.EpisodeType
import com.tv.maze.data.models.navigation.ShowType
import com.tv.maze.main.viewmodels.MainViewModel

enum class Route(val value: String) {
    SHOW_LIST_SCREEN("shows"),
    SHOW_DETAILS_SCREEN("shows/{show}"),
    EPISODE_DETAILS_SCREEN("episodes/{episode}"),
    FAVORITE_SHOWS_SCREEN("shows/favorite"),
    PERSON_DETAILS_SCREEN("people/{person}")
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MainNavigation(
    viewModel: MainViewModel
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
                people = viewModel.people,
                onQueryChange = { newQuery ->
                    viewModel.onQueryChange(newQuery)
                },
                onShowClick = { show ->
                    viewModel.getSeasonsByShow(show.id)
                    val json = Uri.encode(Gson().toJson(show))
                    navController.navigate(Route.SHOW_DETAILS_SCREEN.value.replace("{show}", json))
                },
                onPersonClick = { person ->
                    //TODO pass Person and open PersonDetailsScreen
//                    val json = Uri.encode(Gson().toJson(show))
//                    navController.navigate(Route.SHOW_DETAILS_SCREEN.value.replace("{show}", json))
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
                    onEpisodeClick = { episode ->
                        val json = Uri.encode(Gson().toJson(episode))
                        navController.navigate(
                            Route.EPISODE_DETAILS_SCREEN.value.replace(
                                "{episode}",
                                json
                            )
                        )
                    }
                )
            }
        }
        composable(
            Route.EPISODE_DETAILS_SCREEN.value,
            arguments = listOf(navArgument("episode") { type = EpisodeType() }),
            enterTransition = {
                routeEnterTransition()
            },
            exitTransition = {
                routeExitTransition()
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Episode>("episode")?.let { episode ->
                EpisodeDetailsScreen(episode = episode)
            }
        }
    }
}

private fun routeEnterTransition(): EnterTransition =
    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))

private fun routeExitTransition(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))