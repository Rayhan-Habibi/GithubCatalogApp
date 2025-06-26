package com.example.mandarinkatalog.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.mandarinkatalog.dataStore.UserPreferences
import com.example.mandarinkatalog.screens.detail.Detail
import com.example.mandarinkatalog.screens.home.Home
import com.example.mandarinkatalog.screens.home.HomeViewModel
import com.example.mandarinkatalog.screens.login.Login
import com.example.mandarinkatalog.screens.login.LoginReal
import com.example.mandarinkatalog.screens.login.LoginRealViewModel
import com.example.mandarinkatalog.screens.login.LoginViewModel
import com.example.mandarinkatalog.screens.profile.Profile
import com.example.mandarinkatalog.screens.search.Search
import com.example.mandarinkatalog.screens.splashscreen.SplashScreen
import com.example.mandarinkatalog.screens.starred.Starred
import com.example.mandarinkatalog.ui.theme.Container
import com.example.mandarinkatalog.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(navController: NavHostController, userPreferences: UserPreferences) {
    val loginState by userPreferences.getLoginState().collectAsState(initial = false)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val route = navBackStackEntry?.destination?.route
    val repoName = navBackStackEntry?.arguments?.getString("repoName")


    val title = when {
        route == "home" -> "Repos"
        route == "search" -> "Search"
        route == "profile" -> "Profile"
        route == "starred" -> "Starred"
        route?.startsWith("detail/") == true -> repoName ?: "Repository"
        else -> route ?: ""
    }

    // Navigate to "home" if already logged in and currently on "login"
    LaunchedEffect(loginState, currentRoute) {
        if (loginState && (currentRoute == "login" || currentRoute == "loginreal")) {
            navController.navigate("home") {
                popUpTo(currentRoute!!) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            if (currentRoute != "login" && currentRoute != "loginreal" && currentRoute != "splash") {
                TopAppBar(
                    title = { Text(title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Container,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        bottomBar = {
            if (currentRoute != "login" && currentRoute != "loginreal" && currentRoute != "splash") {
                NavigationBar(containerColor = Container) {
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = { navController.navigate("home") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Repos") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black,
                            indicatorColor = Primary
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "profile",
                        onClick = { navController.navigate("profile") },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black,
                            indicatorColor = Primary
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "starred",
                        onClick = { navController.navigate("starred") },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Starred") },
                        label = { Text("Starred") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black,
                            indicatorColor = Primary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash", // static start
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") {
                val loginState = userPreferences.getLoginState().collectAsState(initial = null)
                SplashScreen(navController, loginState )
            }
            composable("login") {
                val viewModel: LoginViewModel = hiltViewModel()
                Login(viewModel, navController)
            }
            composable("home") {
                val viewModel: HomeViewModel = hiltViewModel()
                Home(viewModel, navController)
            }
            composable("search") { Search() }
            composable("profile") { Profile(navController = navController) }
            composable("starred") {
                val viewModel: HomeViewModel = hiltViewModel()
                Starred(viewModel, navController) }
            composable("loginreal") {
                val viewModel: LoginRealViewModel = hiltViewModel()
                LoginReal(navController = navController, viewModel = viewModel)
            }
            composable(
                route = "detail/{repoName}",
                arguments = listOf(navArgument("repoName") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val repoName = backStackEntry.arguments?.getString("repoName") ?: ""
                val viewModel: HomeViewModel = hiltViewModel()
                Detail(
                    repoName = repoName,
                    viewModel = viewModel
                )
            }

        }
    }
}
