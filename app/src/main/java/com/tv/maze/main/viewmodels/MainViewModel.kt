package com.tv.maze.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.data.TVMazeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tvMazeRepository: TVMazeRepository
) : ViewModel() {

    fun getAllShows() {
        viewModelScope.launch {
            val response = tvMazeRepository.getAllShows()
            println(response.body()?.size)
        }
    }
}