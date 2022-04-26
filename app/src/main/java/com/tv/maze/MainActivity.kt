package com.tv.maze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.widgets.ShowView

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
                    ShowView(
                        posterUrl = "https://as01.epimg.net/epik/imagenes/2018/03/13/portada/1520946522_348122_1520949182_noticia_normal.jpg",
                        name = "Black Mirror"
                    )
                }
            }
        }
    }
}
