package com.tv.maze.main.widgets.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tv.maze.R
import com.tv.maze.data.models.Show
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.DataMocks

@Composable
fun ShowView(
    show: Show,
    onShowClick: (Show) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .border(width = .5.dp, color = Color.Black)
            .clickable {
                onShowClick(show)
            }) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(show.image?.original)
                .error(R.drawable.show_avatar)
                .placeholder(R.drawable.show_avatar)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            text = show.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp, bottom = 16.dp)
                .fillMaxWidth(),
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, heightDp = 250, widthDp = 200)
@Composable
fun ShowViewPreview() {
    TVmazeTheme {
        ShowView(show = DataMocks.show, onShowClick = { })
    }
}