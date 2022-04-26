package com.tv.maze.main.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.R
import com.tv.maze.data.models.Show
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status

@Composable
fun ShowListScreen(
    shows: Resource<ArrayList<Show>>,
    onQueryChange: (String) -> Unit,
    onShowClick: (Int) -> Unit
) {
    Column {
        SearchView(onQueryChange)

        when (shows.status) {
            Status.LOADING -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 50.dp)
                    )
                }
            }
            Status.SUCCESS -> {
                LazyColumn {

                    shows.data?.forEach { show ->
                        item {
                            ShowView(
                                id = show.id,
                                posterUrl = show.image?.original,
                                name = show.name,
                                onShowClick = onShowClick
                            )
                        }
                    }

                }
            }
            Status.ERROR -> {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = shows.message ?: "Could not perform the request",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 50.dp),
                        color = Color.Black,
                    )

                }
            }
        }
    }

}

@Composable
fun SearchView(onQueryChange: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

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
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Clear icon",
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
}