package com.tv.maze.main.widgets.views

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.utils.toAnnotatedString

@Composable
fun SubtopicView(title: String, content: String) {
    Column {
        Text(
            text = "$title:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
        Text(
            text = Html.fromHtml(content).toAnnotatedString(),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 20.dp, end = 16.dp)
                .fillMaxWidth(),
            color = Color.Black,
        )
    }
}

