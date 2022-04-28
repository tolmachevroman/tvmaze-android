package com.tv.maze.ui.authentication.widgets

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.tv.maze.R
import com.tv.maze.ui.theme.TVmazeTheme

@Composable
fun PasswordTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    var passwordVisible: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var value: String by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = value,
        label = label,
        enabled = enabled,
        isError = isError,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Crossfade(
                    targetState = passwordVisible,
                ) { visible ->
                    Icon(
                        painter = painterResource(
                            id = if (visible) {
                                R.drawable.ic_visibility_on
                            } else {
                                R.drawable.ic_visibility_off
                            }
                        ),
                        contentDescription = stringResource(R.string.toggle_password_visibility),
                    )
                }
            }
        },
        onValueChange = { newValue ->
            value = newValue
            onValueChange(newValue)
        },
        modifier = modifier,
        colors = colors,
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    TVmazeTheme {
        PasswordTextField(
            onValueChange = { }
        )
    }
}