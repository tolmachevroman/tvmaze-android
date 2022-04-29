package com.tv.maze.ui.authentication.widgets.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tv.maze.R
import com.tv.maze.ui.authentication.widgets.views.PasswordTextField

@Composable
fun CreatePinScreen(
    navController: NavController,
    isPinEmpty: Boolean,
    error: String?,
    onPinChange: (String) -> Unit,
    onCreatePin: () -> Unit,
    onResetPin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.set_up_pin)
                    )
                },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = {
                            onResetPin()
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    ) {
        it.calculateBottomPadding()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.create_new_pin_code),
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.create_new_pin_code_and_use_it_later),
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField(
                onValueChange = { value -> onPinChange(value) },
                label = { Text(text = stringResource(R.string.pin)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                error = error,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = !isPinEmpty && error.isNullOrEmpty(),
                onClick = {
                    onCreatePin()
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.create_and_login)
                )
            }
        }
    }


}