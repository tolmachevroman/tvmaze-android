package com.tv.maze.ui.main.widgets.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tv.maze.R
import com.tv.maze.data.models.Person
import com.tv.maze.data.models.Show
import com.tv.maze.ui.main.widgets.views.ErrorView
import com.tv.maze.ui.main.widgets.views.LoadingView
import com.tv.maze.ui.main.widgets.views.SubtopicView
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status
import kotlin.math.min

@Composable
fun PersonDetailsScreen(
    navController: NavController,
    person: Person,
    showsPersonParticipatedIn: Resource<ArrayList<Show>>
) {
    val scrollState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = person.name,
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

            item {
                Text(
                    text = stringResource(R.string.participated_in),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                )
            }
            when (showsPersonParticipatedIn.status) {
                Status.LOADING -> {
                    item { LoadingView() }
                }
                Status.SUCCESS -> {
                    if (showsPersonParticipatedIn.data.isNullOrEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.unknown_shows_list),
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(start = 20.dp, end = 16.dp)
                                    .fillMaxWidth(),
                                color = Color.Black,
                            )
                        }
                    } else {
                        showsPersonParticipatedIn.data.forEach { show ->
                            item {
                                ClickableText(
                                    text = AnnotatedString("- ${show.name}"),
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    onClick = {
                                        show.url?.let { link -> uriHandler.openUri(link) }
                                    },
                                    modifier = Modifier
                                        .padding(
                                            start = 20.dp,
                                            end = 16.dp,
                                            top = 3.dp,
                                            bottom = 3.dp
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }


                }
                Status.ERROR -> {
                    item { ErrorView(showsPersonParticipatedIn.message) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailsScreenPreview() {
    TVmazeTheme {
        PersonDetailsScreen(
            navController = rememberNavController(),
            person = DataMocks.person,
            showsPersonParticipatedIn = Resource.loading(null)
        )
    }
}