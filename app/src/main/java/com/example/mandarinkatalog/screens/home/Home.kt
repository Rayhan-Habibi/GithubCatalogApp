package com.example.mandarinkatalog.screens.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mandarinkatalog.components.cards.PrettyCard
import com.example.mandarinkatalog.ui.theme.Container
import com.example.mandarinkatalog.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Home (
    viewModel: HomeViewModel,
){

    var presses by remember { mutableIntStateOf(0) }
    val repos by viewModel.repos.observeAsState(initial = emptyList())
    val error by viewModel.error.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchRepos()
    }

    LaunchedEffect(Unit) {
        if (repos.isNotEmpty()) {
            viewModel.saveReposToDb(context, repos)
        }
        viewModel.loadReposFromDb(context = context)
    }

    if (!error.isNullOrEmpty()) {
        Text(text = error ?: "")
    } else {
        LazyColumn {
            items(repos) { repoItem ->
                PrettyCard(
                    name = repoItem.name ?: "No Name",
                    desc = repoItem.description ?: "No Description"
                )
            }
        }
    }
}