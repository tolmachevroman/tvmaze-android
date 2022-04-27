package com.tv.maze.main.widgets.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import com.tv.maze.R
import com.tv.maze.data.models.Person

@Composable
fun PersonView(
    person: Person,
    onPersonClick: (Person) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                onPersonClick(person)
            }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(person.image?.medium)
                    .size(Size.ORIGINAL)
                    .error(
                        if (person.gender == "female") {
                            R.drawable.female_avatar
                        } else {
                            R.drawable.male_avatar
                        }
                    )
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = person.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .fillMaxWidth(),
                color = Color.Black,
            )
        }

        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )
    }
}