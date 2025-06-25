package com.example.mandarinkatalog.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.core.util.component1
import androidx.core.util.component2
import com.example.mandarinkatalog.components.cards.PrettyCard
import kotlin.collections.firstOrNull
import androidx.navigation.NavHostController

@Composable
fun Profile(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val repos by viewModel.repos.observeAsState(initial = emptyList())
    val error by viewModel.error.observeAsState()
    val username by viewModel.username.observeAsState("")

    // Load username and repos
    LaunchedEffect(Unit) {
        viewModel.loadUsername()
        viewModel.fetchRepos()
        viewModel.loadReposFromDb(context)
    }

    // Filter repo that matches logged-in user
    val currentUserRepo = repos.firstOrNull { it.owner?.login == username }

    if (!error.isNullOrEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Add screen padding
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = error ?: "")
                Button(
                    onClick = {
                        navController.navigate("login")
                        viewModel.logoutUser()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Logout")
                }
            }
        }
    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Add screen padding
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                currentUserRepo?.owner?.avatarUrl?.let { avatarUrl ->
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier.size(80.dp)
                    )
                } ?: Text("No profile picture found")

                Row {

                    Text("Github Username: ")
                    currentUserRepo?.owner?.login.let { name ->
                        Text(name.toString())
                    }
                }

                Button(
                    onClick = {
                        navController.navigate("login")
                        viewModel.logoutUser()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Logout")
                }

                Row {
                    Column {

                    }
                }
            }
        }
    }
}
