package com.tv.maze.main.widgets.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.data.models.Person

@Composable
fun PersonView(
    person: Person,
    onPersonClick: (Person) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onPersonClick(person)
            }
    ) {
        val imageUrl =
            person.image?.original ?: person.image?.medium
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = person.name,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
    }
}