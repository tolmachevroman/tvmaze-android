package com.tv.maze.ui.main.widgets.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tv.maze.R
import com.tv.maze.data.models.Episode
import com.tv.maze.ui.main.widgets.views.SubtopicView
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks
import kotlin.math.min

@Composable
fun EpisodeDetailsScreen(
    navController: NavController,
    episode: Episode
) {
    val scrollState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = episode.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    ) {
        it.calculateBottomPadding()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(episode.image?.original)
                        .error(R.drawable.show_avatar)
                        .placeholder(R.drawable.show_avatar)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .graphicsLayer {
                            scrolledY += scrollState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledY * 0.5f
                            previousOffset = scrollState.firstVisibleItemScrollOffset
                            alpha = min(1f, 1 - (scrolledY / 800f))
                        }
                        .padding(bottom = 6.dp)
                )
            }
            item { SubtopicView(title = stringResource(R.string.name), content = episode.name) }
            item {
                SubtopicView(
                    title = stringResource(R.string.number),
                    content = "${episode.number}"
                )
            }
            item {
                SubtopicView(
                    title = stringResource(R.string.season),
                    content = "${episode.season}"
                )
            }
            item {
                SubtopicView(
                    title = stringResource(R.string.summary),
                    content = if (episode.summary.isNullOrEmpty()) {
                        stringResource(R.string.unknown_summary)
                    } else {
                        episode.summary.trim()
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EpisodeDetailsScreenPreview() {
    TVmazeTheme {
        DataMocks.seasons[1].episodes?.get(0)?.let { episode ->
            EpisodeDetailsScreen(
                navController = rememberNavController(),
                episode = episode
            )
        }
    }
}