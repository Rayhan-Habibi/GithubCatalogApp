package com.example.mandarinkatalog.screens.ScaffoldHolder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mandarinkatalog.components.cards.PrettyCard
import com.example.mandarinkatalog.ui.theme.Container
import com.example.mandarinkatalog.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldHolder(){
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

        }
    }
}