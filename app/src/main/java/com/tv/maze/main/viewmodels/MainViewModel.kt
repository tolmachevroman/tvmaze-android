package com.tv.maze.main.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.data.TVMazeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stringResources: Resources,
    private val sharedPreferences: SharedPreferences,
    private val tvMazeRepository: TVMazeRepository
) : ViewModel() {

    fun getAllShows() {
        viewModelScope.launch {
            val response = tvMazeRepository.getAllShows()
            println(response.body()?.size)
        }
    }
}