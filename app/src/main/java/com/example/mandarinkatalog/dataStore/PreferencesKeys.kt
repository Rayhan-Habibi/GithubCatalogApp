package com.example.mandarinkatalog.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val GITHUB_USERNAME = stringPreferencesKey("github_username")
}