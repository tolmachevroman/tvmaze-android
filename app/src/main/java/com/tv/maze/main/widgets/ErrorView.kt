package com.tv.maze.main.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.R

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