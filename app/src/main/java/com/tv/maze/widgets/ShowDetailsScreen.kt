package com.tv.maze.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.ui.theme.TVmazeTheme

@Composable
fun ShowDetailsScreen(
    posterUrl: String,
    name: String,
    days: ArrayList<String>,
    time: String,
    genres: ArrayList<String>,
    summary: String
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )
        Subtopic(title = "Name", content = name)
        Subtopic(title = "Airing on", content = days.joinToString(separator = ",") + " at " + time)
        Subtopic(title = "Genres", content = genres.joinToString(separator = ","))
        Subtopic(
            title = "Summary",
            content = summary
        )
        //TODO list of episodes separated by season
    }
}

@Composable
fun Subtopic(title: String, content: String) {
    Column {
        Text(
            text = "$title:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
        Text(
            text = content,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDetailsScreenPreview() {
    TVmazeTheme {
        ShowDetailsScreen(
            posterUrl = "",
            name = "Black Mirror",
            days = arrayListOf("Wednesday, Thursday"),
            time = "22:00",
            genres = arrayListOf("Drama, Thriller"),
            summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours."
        )
    }
}