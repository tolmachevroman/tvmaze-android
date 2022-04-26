package com.tv.maze

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.tv.maze.models.Episode
import com.tv.maze.models.Poster
import com.tv.maze.models.Season
import com.tv.maze.ui.theme.TVmazeTheme
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
                    val seasons = arrayListOf(
                        Season(
                            id = 1,
                            number = 1,
                            episodes = arrayListOf(
                                Episode(
                                    id = 1,
                                    name = "The National Anthem",
                                    season = 1,
                                ),
                                Episode(
                                    id = 2,
                                    name = "Fifteen Million Merits",
                                    season = 1,
                                ),
                                Episode(
                                    id = 3,
                                    name = "The Entire History of You",
                                    season = 1,
                                )
                            )
                        ),
                        Season(
                            id = 2,
                            number = 2,
                            episodes = arrayListOf(
                                Episode(
                                    id = 4,
                                    name = "Be Right Back",
                                    season = 2,
                                ),
                                Episode(
                                    id = 5,
                                    name = "White Bear",
                                    season = 2,
                                ),
                                Episode(
                                    id = 6,
                                    name = "The Waldo Moment",
                                    season = 2,
                                )
                            )
                        ),
                        Season(
                            id = 3,
                            number = 3,
                            episodes = arrayListOf(
                                Episode(
                                    id = 7,
                                    name = "Nosedive",
                                    season = 3,
                                ),
                                Episode(
                                    id = 8,
                                    name = "Playtest",
                                    season = 3,
                                ),
                                Episode(
                                    id = 9,
                                    name = "Shut Up and Dance",
                                    season = 3,
                                ),
                                Episode(
                                    id = 10,
                                    name = "San Junipero",
                                    season = 3,
                                )
                            )
                        )
                    )
                    ShowDetailsScreen(
                        posterUrl = "https://as01.epimg.net/epik/imagenes/2018/03/13/portada/1520946522_348122_1520949182_noticia_normal.jpg",
                        name = "Black Mirror",
                        days = arrayListOf("Wednesday, Thursday"),
                        time = "22:00",
                        genres = arrayListOf("Drama, Thriller"),
                        summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
                        seasons = seasons,
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
