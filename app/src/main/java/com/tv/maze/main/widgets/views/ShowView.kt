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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.tv.maze.data.models.Show
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.R
import com.tv.maze.data.models.Poster
import com.tv.maze.data.models.Schedule

@Composable
fun ShowView(
    show: Show,
    onShowClick: (Show) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                onShowClick(show)
            }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(show.image?.medium)
                    .size(Size.ORIGINAL)
                    .error(R.drawable.show_avatar)
                    .placeholder(R.drawable.show_avatar)
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
                text = show.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp)
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

@Preview(showBackground = true, heightDp = 140)
@Composable
fun ShowViewPreview() {
    TVmazeTheme {
        val show = Show(
            id = 1,
            name = "Black Mirror",
            genres = arrayListOf("Sci-Fi", "Drama", "Thriller"),
            image = Poster(
                medium = "https://as01.epimg.net/epik/imagenes/2018/03/13/portada/1520946522_348122_1520949182_noticia_normal.jpg",
                original = null
            ),
            summary = "In an abstrusely dystopian future, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours.",
            schedule = Schedule(time = "22:00", days = arrayListOf("Wednesday", "Friday"))
        )
        ShowView(show = show, onShowClick = { })
    }
}