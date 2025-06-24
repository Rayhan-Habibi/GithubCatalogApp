package com.example.mandarinkatalog.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mandarinkatalog.dataStore.PreferencesKeys.GITHUB_USERNAME
import com.example.mandarinkatalog.dataStore.PreferencesKeys.IS_LOGGED_IN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun setLoginState(isLoggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = isLoggedIn
        }
    }

    fun getLoginState(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_LOGGED_IN] ?: false
        }
    }

    suspend fun saveUserData(githubUsername: String) {
        context.dataStore.edit { prefs ->
            prefs[GITHUB_USERNAME] = githubUsername
        }
    }

    fun getUserData(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[GITHUB_USERNAME]
        }
    }
}
