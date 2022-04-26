package com.tv.maze

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.SeasonsMocks
import com.tv.maze.widgets.ShowDetailsScreen

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
                    ShowDetailsScreen(
                        posterUrl = "https://as01.epimg.net/epik/imagenes/2018/03/13/portada/1520946522_348122_1520949182_noticia_normal.jpg",
                        name = "Black Mirror",
                        days = arrayListOf("Wednesday, Thursday"),
                        time = "22:00",
                        genres = arrayListOf("Drama, Thriller"),
                        summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
                        seasons = SeasonsMocks.seasons,
                        onEpisodeClick = {
                            Toast.makeText(this, "Clicked episode id $it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            }
        }
    }
}
