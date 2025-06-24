package com.example.mandarinkatalog.screens.login

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mandarinkatalog.dataStore.UserPreferences
import com.example.mandarinkatalog.database.UserContract
import com.example.mandarinkatalog.database.UserHelper
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn: StateFlow<Boolean> = _loggedIn

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var github by mutableStateOf("")

    init {
        viewModelScope.launch {
            userPreferences.getLoginState()
                .collect { state ->
                    _loggedIn.value = state
                }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            userPreferences.setLoginState(true)
            userPreferences.saveUserData(github)
        }
    }

    fun createUserData(context: Context) {
        viewModelScope.launch {
            val dbHelper = UserHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(UserContract.UserData.COLUMN_NAME_USERNAME, username)
                put(UserContract.UserData.COLUMN_NAME_PASSWORD, password)
                put(UserContract.UserData.COLUMN_NAME_GITHUB, github)
            }

            val newRowId = db.insert(UserContract.UserData.TABLE_NAME, null, values)
            Log.d("DB_INSERT", "Inserted row ID: $newRowId")
        }
    }
}
