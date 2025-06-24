package com.example.mandarinkatalog.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mandarinkatalog.dataStore.UserPreferences
import com.example.mandarinkatalog.screens.home.Home
import com.example.mandarinkatalog.screens.home.HomeViewModel
import com.example.mandarinkatalog.screens.login.Login
import com.example.mandarinkatalog.screens.login.LoginViewModel
import com.example.mandarinkatalog.ui.theme.Container
import com.example.mandarinkatalog.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(navController: NavHostController, userPreferences: UserPreferences) {

    val context = LocalContext.current
    val loginStateFlow = userPreferences.getLoginState().collectAsState(initial = false)

    val startDestination = if (loginStateFlow.value) "home" else "login"

    if (startDestination == "login"){
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                val viewModel: LoginViewModel = hiltViewModel()
                Login(viewModel, navController)
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Container,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Your Repositories")
                    }
                )
            },
            bottomBar = {
                BottomAppBar(

                ) {
                    NavigationBar(
                        containerColor = Container
                    ) {
                        NavigationBarItem(
                            selected = true,
                            onClick = { /* handle home */ },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black,
                                indicatorColor = Primary
                            ),
                            label = { Text("Repos") }

                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { /* handle search */ },
                            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black,
                                indicatorColor = Primary
                            ),
                            label = { Text("Search") }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { /* handle profile */ },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black,
                                indicatorColor = Primary
                            ),
                            label = { Text("Profile") }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { /* handle profile */ },
                            icon = { Icon(Icons.Default.Star, contentDescription = "Profile") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black,
                                indicatorColor = Primary
                            ),
                            label = { Text("Starred") }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                NavHost(navController, startDestination = startDestination) {
                    composable("home") {
                        val viewModel: HomeViewModel = hiltViewModel()
                        Home(viewModel)
                    }
                }
            }
        }


    }
}
