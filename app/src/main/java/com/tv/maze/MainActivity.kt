package com.tv.maze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.SeasonsMocks
import com.tv.maze.widgets.EpisodeDetailsScreen

class MainActivity : ComponentActivity() {
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
