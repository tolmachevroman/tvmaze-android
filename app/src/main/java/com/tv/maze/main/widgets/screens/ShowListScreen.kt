package com.tv.maze.main.widgets.screens

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.R
import com.tv.maze.data.models.Person
import com.tv.maze.data.models.Show
import com.tv.maze.main.widgets.views.ErrorView
import com.tv.maze.main.widgets.views.LoadingView
import com.tv.maze.main.widgets.views.PersonView
import com.tv.maze.main.widgets.views.ShowView
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowListScreen(
    shows: Resource<List<Show>>,
    people: Resource<List<Person>>,
    onQueryChange: (String) -> Unit,
    onShowClick: (Show) -> Unit,
    onPersonClick: (Person) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var query by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        TextField(
            value = query,
            onValueChange = { newQuery ->
                query = newQuery
                if (newQuery.isNotEmpty()) {
                    onQueryChange(newQuery)
                    showClearIcon = true
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = stringResource(R.string.search_icon_description)
                )
            },
            trailingIcon = {
                if (showClearIcon) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = stringResource(R.string.clear_icon_description),
                            modifier = Modifier.clickable {
                                showClearIcon = false
                                query = ""
                                onQueryChange("")
                            }
                        )
                    }
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            placeholder = { Text(text = stringResource(R.string.hint_search_query)) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background, shape = RectangleShape)
        )

        when (shows.status) {
            Status.LOADING -> {
                LoadingView()
            }
            Status.SUCCESS -> {
                LazyColumn(modifier = Modifier.pointerInteropFilter {
                    if (it.action == MotionEvent.ACTION_DOWN) {
                        keyboardController?.hide()
                        false
                    } else true
                }) {

                    if (query.isNotEmpty()) {
                        item {
                            HeaderView(title = stringResource(R.string.shows_found))
                        }
                    }

                    shows.data?.forEach { show ->
                        item {
                            ShowView(
                                show = show,
                                onShowClick = {
                                    keyboardController?.hide()
                                    onShowClick(show)
                                }
                            )
                        }
                    }

                    if (query.isNotEmpty()) {
                        item {
                            HeaderView(title = stringResource(R.string.people_found))
                        }

                        people.data?.forEach { person ->
                            item {
                                PersonView(
                                    person = person,
                                    onPersonClick = {
                                        keyboardController?.hide()
                                        onPersonClick(person)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Status.ERROR -> {
                ErrorView(shows.message)
            }
        }
    }
}

@Composable
fun HeaderView(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray),
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp),
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowListScreenPreview() {
    TVmazeTheme {
        val shows = Resource.success(arrayListOf(DataMocks.show, DataMocks.show, DataMocks.show))
        val people = Resource.success(arrayListOf(DataMocks.person))
        ShowListScreen(
            shows = shows,
            people = people,
            onQueryChange = { },
            onShowClick = {},
            onPersonClick = {})
    }
}