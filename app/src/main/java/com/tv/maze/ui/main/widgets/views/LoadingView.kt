package com.tv.maze.ui.main.widgets.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tv.maze.ui.theme.TVmazeTheme

@Composable
fun LoadingView() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingViewPreview() {
    TVmazeTheme {
        LoadingView()
    }
}