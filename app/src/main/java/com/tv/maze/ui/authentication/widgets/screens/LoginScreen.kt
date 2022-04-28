package com.tv.maze.ui.authentication.widgets.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tv.maze.R
import com.tv.maze.ui.authentication.widgets.PasswordTextField

@Composable
fun LoginScreen(
    error: String?,
    onPinChange: (String) -> Unit,
    onLogin: () -> Unit,
    onCreatePin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.welcome),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.login_or_create_new_pin_code),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            onValueChange = { value -> onPinChange(value) },
            label = { Text(text = stringResource(R.string.pin)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            isError = error != null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onLogin() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.enter)
            )
        }
        Text(
            text = stringResource(R.string.or),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = {
                onCreatePin()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.set_up_pin)
            )
        }
    }
}