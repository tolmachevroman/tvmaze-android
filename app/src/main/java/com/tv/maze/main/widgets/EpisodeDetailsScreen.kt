package com.tv.maze.main.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.data.models.Episode

@Composable
fun EpisodeDetailsScreen(
    episode: Episode
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageUrl = episode.image?.original ?: episode.image?.medium
        imageUrl?.let {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }
        }

        item { SubtopicView(title = "Name", content = episode.name) }
        item { SubtopicView(title = "Number", content = "${episode.number}") }
        item { SubtopicView(title = "Season", content = "${episode.season}") }
        item { SubtopicView(title = "Summary", content = episode.summary) }
    }
}