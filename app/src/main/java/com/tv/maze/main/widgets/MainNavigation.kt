package com.tv.maze.main.widgets

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.Person
import com.tv.maze.data.models.Show
import com.tv.maze.data.models.navigation.EpisodeType
import com.tv.maze.data.models.navigation.PersonType
import com.tv.maze.data.models.navigation.ShowType
import com.tv.maze.main.viewmodels.MainViewModel
import com.tv.maze.main.widgets.screens.EpisodeDetailsScreen
import com.tv.maze.main.widgets.screens.PersonDetailsScreen
import com.tv.maze.main.widgets.screens.ShowDetailsScreen
import com.tv.maze.main.widgets.screens.ShowListScreen

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
                    val json = Uri.encode(Gson().toJson(person))
                    navController.navigate(
                        Route.PERSON_DETAILS_SCREEN.value.replace(
                            "{person}",
                            json
                        )
                    )
                }
            )
        }
        composable(
            Route.SHOW_DETAILS_SCREEN.value,
            arguments = listOf(navArgument("show") { type = ShowType() }),
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
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Episode>("episode")?.let { episode ->
                EpisodeDetailsScreen(episode)
            }
        }
        composable(
            Route.PERSON_DETAILS_SCREEN.value,
            arguments = listOf(navArgument("person") { type = PersonType() }),
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Person>("person")?.let { person ->
                PersonDetailsScreen(person)
            }
        }
    }
}
