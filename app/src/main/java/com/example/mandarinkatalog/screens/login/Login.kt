package com.example.mandarinkatalog.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Login(
    viewModel: LoginViewModel,
    navController: NavHostController
) {

    var context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Add screen padding
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Adds space between items
        ) {
            Text(text = "Register", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = viewModel.username,
                label = { Text("Username") },
                onValueChange = { viewModel.username = it }
            )

            OutlinedTextField(
                value = viewModel.password,
                label = { Text("Password") },
                onValueChange = { viewModel.password = it }
            )
            OutlinedTextField(
                value = viewModel.github,
                label = { Text("Github Username") },
                onValueChange = { viewModel.github = it }
            )

            Button(onClick = {
                viewModel.createUserData(context)
                viewModel.loginUser()
                navController.navigate("home")
            }) {
                Text("Submit")
            }
        }
    }
}
