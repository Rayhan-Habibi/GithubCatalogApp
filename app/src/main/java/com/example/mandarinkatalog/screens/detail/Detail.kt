package com.example.mandarinkatalog.screens.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mandarinkatalog.components.cards.FavoriteButton
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

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("id: ${item.id.toString()}")
                    Text(
                        text = item.name,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1C1C1E),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 24.dp)
                    )
                    FavoriteButton(
                        isFavorite = item.starred == 1,
                        onToggle = {
                            viewModel.toggleStarred(context, item.id ?: 0, item.starred != 1)
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val fullDate = item.createdAt
                                val trimmedDate = fullDate?.substringBefore("T")
                                Text("Created At:")
                                Text(trimmedDate.toString())

                            }

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val fullDate = item.updatedAt
                                val trimmedDate = fullDate?.substringBefore("T")
                                Text("Updated At:")
                                Text(trimmedDate.toString())
                            }
                        }
                    }
                }

            }
        }
    }

}