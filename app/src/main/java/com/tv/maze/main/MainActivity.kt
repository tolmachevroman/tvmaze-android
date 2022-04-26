package com.tv.maze.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.tv.maze.main.viewmodels.MainViewModel
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.SeasonsMocks
import com.tv.maze.main.widgets.EpisodeDetailsScreen

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVmazeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EpisodeDetailsScreen(episode = SeasonsMocks.seasons[1].episodes[0])
                }
            }
        }
    }
}
