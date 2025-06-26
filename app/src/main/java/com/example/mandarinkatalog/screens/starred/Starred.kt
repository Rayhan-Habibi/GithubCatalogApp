package com.example.mandarinkatalog.screens.starred

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mandarinkatalog.components.cards.PrettyCard
import com.example.mandarinkatalog.screens.home.HomeViewModel

@Composable
fun Starred(
    viewModel: HomeViewModel,
    navController: NavHostController
){
    val context = LocalContext.current
    val starredRepos = viewModel.starredRepos.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadStarredReposFromDb(context)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        val starredRepos = starredRepos.value

        if (starredRepos.isEmpty()) {
            Text("No starred repositories")
        } else {
            starredRepos.forEach { repo ->
                PrettyCard(
                    name = repo.name ?: "No Name",
                    desc = repo.description ?: "No Description",
                    onClick = {
                        viewModel.selectRepo(repo)
                        repo.name?.let { name ->
                            val encoded = Uri.encode(repo.name)
                            navController.navigate("detail/$encoded")
                        }
                    }
                )
            }
        }
    }
}