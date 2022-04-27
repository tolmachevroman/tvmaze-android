package com.tv.maze.main.widgets.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tv.maze.R
import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import com.tv.maze.main.widgets.views.ErrorView
import com.tv.maze.main.widgets.views.LoadingView
import com.tv.maze.main.widgets.views.SubtopicView
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status
import kotlin.math.min

@Composable
fun ShowDetailsScreen(
    show: Show,
    seasonsByShow: Resource<ArrayList<Season>>,
    onEpisodeClick: (Episode) -> Unit
) {
    val scrollState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState
    ) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(show.image?.original)
                    .error(R.drawable.show_avatar)
                    .placeholder(R.drawable.show_avatar)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .graphicsLayer {
                        scrolledY += scrollState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = scrollState.firstVisibleItemScrollOffset
                        alpha = min(1f, 1 - (scrolledY / 800f))
                    }
                    .padding(bottom = 6.dp)
            )
        }
        item { SubtopicView(title = stringResource(R.string.name), content = show.name) }
        item {
            SubtopicView(
                title = stringResource(R.string.airing_on),
                content = if (show.schedule != null && show.schedule.days.isNotEmpty() && show.schedule.time.isNotBlank()) {
                    stringResource(
                        R.string.schedule,
                        show.schedule.days.joinToString(separator = ", "),
                        show.schedule.time
                    )
                } else {
                    stringResource(R.string.unknown_schedule)
                }
            )
        }
        item {
            SubtopicView(
                title = stringResource(R.string.genres),
                content = if (show.genres.isNullOrEmpty()) {
                    stringResource(R.string.unknown_genres)
                } else {
                    show.genres.joinToString(separator = ", ")
                }
            )
        }
        item {
            SubtopicView(
                title = stringResource(R.string.summary),
                content = if (show.summary.isNullOrEmpty()) {
                    stringResource(R.string.unknown_summary)
                } else {
                    show.summary.trim()
                }
            )
        }
        item {
            Text(
                text = stringResource(R.string.seasons),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp)
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
            text = stringResource(R.string.season_number, season.number),
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
        val seasonsByShow = Resource.success(DataMocks.seasons)
        ShowDetailsScreen(show = DataMocks.show, seasonsByShow = seasonsByShow, onEpisodeClick = {})
    }
}