package com.tv.maze.main.widgets

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
import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status

@Composable
fun ShowDetailsScreen(
    show: Show,
    seasonsByShow: Resource<ArrayList<Season>>,
    onEpisodeClick: (Episode) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(show.image?.original)
                    .size(Size.ORIGINAL)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
        item { SubtopicView(title = "Name", content = show.name) }
        item {
            SubtopicView(
                title = "Airing on",
                content = show.schedule.days.joinToString(separator = ", ") + " at " + show.schedule.time
            )
        }
        item {
            SubtopicView(
                title = "Genres",
                content = show.genres.joinToString(separator = ", ")
            )
        }
        item {
            SubtopicView(
                title = "Summary",
                content = show.summary,
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

        when (seasonsByShow.status) {
            Status.LOADING -> {
                item { LoadingView() }
            }
            Status.SUCCESS -> {
                seasonsByShow.data?.forEach { season ->
                    item { SeasonView(season, onEpisodeClick) }
                }
            }
            Status.ERROR -> {
                item { ErrorView(seasonsByShow.message) }
            }
        }
    }
}

@Composable
fun SeasonView(season: Season, onEpisodeClick: (Episode) -> Unit) {
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
        season.episodes?.forEach { episode ->
            EpisodeView(episode, onEpisodeClick)
        }
    }
}

@Composable
fun EpisodeView(episode: Episode, onEpisodeClick: (Episode) -> Unit) {
    ClickableText(
        text = AnnotatedString("- ${episode.name}"),
        style = TextStyle(
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
        ),
        onClick = {
            onEpisodeClick(episode)
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
        //TODO
//        ShowDetailsScreen(
//            posterUrl = "",
//            name = "Black Mirror",
//            days = arrayListOf("Wednesday, Thursday"),
//            time = "22:00",
//            genres = arrayListOf("Drama, Thriller"),
//            summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
//            seasons = SeasonsMocks.seasons,
//            onEpisodeClick = { }
//        )
    }
}