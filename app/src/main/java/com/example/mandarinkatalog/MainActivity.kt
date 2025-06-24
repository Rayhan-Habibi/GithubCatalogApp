package com.example.mandarinkatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mandarinkatalog.navigation.AppNavHost
import com.example.mandarinkatalog.ui.theme.MandarinkatalogTheme
import android.app.Application
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.mandarinkatalog.dataStore.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MandarinkatalogTheme {
                MandarinCatalog()
            }
        }
    }
}

@HiltAndroidApp
class MyApplication : Application()

@Composable
fun MandarinCatalog() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val navController = rememberNavController()
    AppNavHost(navController= navController, userPreferences = userPreferences)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MandarinkatalogTheme {
    }
}