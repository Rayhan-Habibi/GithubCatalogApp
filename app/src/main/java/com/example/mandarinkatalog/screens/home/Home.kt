package com.example.mandarinkatalog.screens.home


import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mandarinkatalog.components.cards.PrettyCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Home (
    viewModel: HomeViewModel,
    navController: NavHostController
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
                    desc = repoItem.description ?: "No Description",
                    onClick = {
                        viewModel.selectRepo(repoItem)
                        repoItem.name?.let { name ->
                            val encoded = Uri.encode(repoItem.name)
                            navController.navigate("detail/$encoded")
                        }
                    }
                )
            }
        }
    }
}