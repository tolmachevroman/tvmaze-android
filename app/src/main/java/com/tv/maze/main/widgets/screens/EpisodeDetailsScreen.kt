package com.tv.maze.main.widgets.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.R
import com.tv.maze.data.models.Episode
import com.tv.maze.main.widgets.views.SubtopicView
import kotlin.math.min

@Composable
fun EpisodeDetailsScreen(
    episode: Episode
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
                    .data(episode.image?.original)
                    .error(R.drawable.show_avatar)
                    .placeholder(R.drawable.show_avatar)
                    .size(Size.ORIGINAL)
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