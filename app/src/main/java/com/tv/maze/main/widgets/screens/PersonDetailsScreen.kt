package com.tv.maze.main.widgets.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tv.maze.R
import com.tv.maze.data.models.Person
import com.tv.maze.main.widgets.views.SubtopicView
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks
import kotlin.math.min

@Composable
fun PersonDetailsScreen(
    person: Person
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
            val placeholder = if (person.gender?.lowercase() == "female") {
                R.drawable.female_avatar
            } else {
                R.drawable.male_avatar
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(person.image?.original)
                    .error(placeholder)
                    .placeholder(placeholder)
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
        item { SubtopicView(title = stringResource(R.string.name), content = person.name) }
        //TODO Series they have participated in, with a link to the series details.
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailsScreenPreview() {
    TVmazeTheme {
        PersonDetailsScreen(person = DataMocks.person)
    }
}