package com.tv.maze.ui.authentication

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tv.maze.ui.authentication.viewmodels.AuthenticationViewModel
import com.tv.maze.ui.authentication.widgets.screens.CreatePinScreen
import com.tv.maze.ui.authentication.widgets.screens.LoginScreen

enum class Route(val value: String) {
    LOGIN_SCREEN("login"),
    CREATE_PIN_SCREEN("createPin"),
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthenticationNavigation(
    viewModel: AuthenticationViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Route.LOGIN_SCREEN.value) {
        composable(
            Route.LOGIN_SCREEN.value,
        ) {
            LoginScreen(
                isPinEmpty = viewModel.isPinEmpty,
                error = viewModel.error,
                onPinChange = { pin ->
                    viewModel.onPinChange(pin)
                },
                onLogin = { viewModel.onLogin() },
                onCreatePin = {
                    navController.navigate(Route.CREATE_PIN_SCREEN.value)
                },
                onResetPin = { viewModel.onResetPin() }
            )
        }
        composable(
            Route.CREATE_PIN_SCREEN.value,
        ) {
            CreatePinScreen(
                navController = navController,
                isPinEmpty = viewModel.isPinEmpty,
                error = viewModel.error,
                onPinChange = { pin ->
                    viewModel.onPinChange(pin)
                },
                onCreatePin = { viewModel.onCreate() },
                onResetPin = { viewModel.onResetPin() }
            )
        }
    }
}