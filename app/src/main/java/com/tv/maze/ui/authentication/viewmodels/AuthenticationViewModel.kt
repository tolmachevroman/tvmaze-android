package com.tv.maze.ui.authentication.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    var error by mutableStateOf<String?>(null)

    fun onPinChange(pin: String) {
        println("On pin change $pin")
    }

    fun onLogin() {

    }
}


