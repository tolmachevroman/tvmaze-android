package com.tv.maze.ui.main.widgets.views

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.ui.theme.TVmazeTheme
import com.tv.maze.utils.toAnnotatedString

@Composable
fun SubtopicView(title: String, content: String) {
    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
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

@Preview(showBackground = true, heightDp = 140)
@Composable
fun SubtopicViewPreview() {
    TVmazeTheme {
        SubtopicView(
            title = "Summary",
            content = "In an abstrusely <b>dystopian future</b>, several individuals grapple with the manipulative effects of cutting edge technology in their personal lives and behaviours."
        )
    }
}