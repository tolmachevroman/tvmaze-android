package com.tv.maze.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.models.Episode
import com.tv.maze.models.Poster
import com.tv.maze.models.Season
import com.tv.maze.ui.theme.TVmazeTheme

@Composable
fun ShowDetailsScreen(
    posterUrl: String,
    name: String,
    days: ArrayList<String>,
    time: String,
    genres: ArrayList<String>,
    summary: String,
    seasons: ArrayList<Season>,
    onEpisodeClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
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
        }
        item { SubtopicView(title = "Name", content = name) }
        item {
            SubtopicView(
                title = "Airing on",
                content = days.joinToString(separator = ",") + " at " + time
            )
        }
        item { SubtopicView(title = "Genres", content = genres.joinToString(separator = ",")) }
        item {
            SubtopicView(
                title = "Summary",
                content = summary
            )
        }
        item {
            Text(
                text = "Seasons:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, top = 10.dp)
                    .fillMaxWidth(),
                color = Color.Black,
            )
        }
        seasons.forEach { season ->
            item { SeasonView(season, onEpisodeClick) }
        }
    }
}

@Composable
fun SubtopicView(title: String, content: String) {
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

@Composable
fun SeasonView(season: Season, onEpisodeClick: (Long) -> Unit) {
    Column {
        Text(
            text = "Season ${season.number}:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
        season.episodes.forEach { episode ->
            EpisodeView(episode, onEpisodeClick)
        }
    }
}

@Composable
fun EpisodeView(episode: Episode, onEpisodeClick: (Long) -> Unit) {
    ClickableText(
        text = AnnotatedString("- ${episode.name}"),
        style = TextStyle(
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
        ),
        onClick = {
            onEpisodeClick(episode.id)
        },
        modifier = Modifier
            .padding(start = 20.dp, end = 16.dp, top = 3.dp, bottom = 3.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun ShowDetailsScreenPreview() {
    TVmazeTheme {
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
            )
        )
        ShowDetailsScreen(
            posterUrl = "",
            name = "Black Mirror",
            days = arrayListOf("Wednesday, Thursday"),
            time = "22:00",
            genres = arrayListOf("Drama, Thriller"),
            summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
            seasons = seasons,
            onEpisodeClick = { }
        )
    }
}