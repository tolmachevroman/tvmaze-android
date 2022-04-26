package com.tv.maze.main.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun ShowView(
    show: Show,
    onShowClick: (Show) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onShowClick(show)
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(show.image?.original)
                .size(Size.ORIGINAL)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = show.name,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true, heightDp = 140)
@Composable
fun ShowViewPreview() {
    TVmazeTheme {
        // TODO
//        ShowView(
//            id = 1,
//            posterUrl = "https://as01.epimg.net/epik/imagenes/2018/03/13/portada/1520946522_348122_1520949182_noticia_normal.jpg",
//            name = "Black Mirror",
//            onShowClick = {  }
//        )
    }
}