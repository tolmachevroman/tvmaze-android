package com.tv.maze.ui.main.widgets.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.R
import com.tv.maze.ui.theme.TVmazeTheme

@Composable
fun ErrorView(errorMessage: String?) {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = errorMessage ?: stringResource(R.string.unknown_error),
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp),
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true, heightDp = 140)
@Composable
fun ErrorViewPreview() {
    TVmazeTheme {
        ErrorView(errorMessage = "Could not load shows")
    }
}