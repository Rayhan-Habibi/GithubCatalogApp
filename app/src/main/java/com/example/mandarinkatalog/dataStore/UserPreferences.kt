package com.example.mandarinkatalog.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

// ✅ Declare at the top-level (outside class)
val Context._dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val LOGIN_STATE_KEY = booleanPreferencesKey("login_state")
    }

    suspend fun saveUserData(username: String) {
        context._dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    fun getUserData(): Flow<String?> {
        return context._dataStore.data.map { prefs ->
            prefs[USERNAME_KEY]
        }
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context._dataStore.edit { prefs ->
            prefs[LOGIN_STATE_KEY] = isLoggedIn
        }
    }

    fun getLoginState(): Flow<Boolean> {
        return context._dataStore.data.map { prefs ->
            prefs[LOGIN_STATE_KEY] ?: false
        }
    }
}
