package com.example.mandarinkatalog.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.mandarinkatalog.screens.home.HomeViewModel

@Composable
fun Detail (
    repoName: String,
    viewModel: HomeViewModel
){

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
    Column {
        for (item in repos){
            if (item.name == repoName){
                Text(item.name)
                Text("Found The repo!")

            }
        }
    }

}