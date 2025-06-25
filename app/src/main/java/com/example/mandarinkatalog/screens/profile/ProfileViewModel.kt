package com.example.mandarinkatalog.screens.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mandarinkatalog.api.ResponseItem
import com.example.mandarinkatalog.api.RetrofitClient
import com.example.mandarinkatalog.dataStore.UserPreferences
import com.example.mandarinkatalog.database.UserHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Callback
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _repos = MutableLiveData<List<ResponseItem>>()
    val repos: LiveData<List<ResponseItem>> = _repos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchRepos() {
        viewModelScope.launch {
            val username = userPreferences.getUserData().first()
            if (!username.isNullOrEmpty()) {
                val call = RetrofitClient.instance.getRepoByUsername(username)
                call.enqueue(object : Callback<List<ResponseItem>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<ResponseItem>>,
                        response: retrofit2.Response<List<ResponseItem>>
                    ) {
                        if (response.isSuccessful) {
                            val repoList = response.body() ?: emptyList()
                            _repos.value = repoList
                        } else {
                            _error.value = "Failed: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<List<ResponseItem>>, t: Throwable) {
                        _error.value = "Error: ${t.message}"
                    }
                })
            } else {
                _error.value = "Username not found"
            }
        }
    }
    fun saveReposToDb(context: Context, repos: List<ResponseItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.getUserData().collect { username ->
                val dbHelper = UserHelper(context)
                dbHelper.saveReposForUser(username.toString(), repos)
            }
        }
    }

    fun loadReposFromDb(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.getUserData().collect { username ->
                val dbHelper = UserHelper(context)
                val repos = dbHelper.getReposForUser(username.toString())
                _repos.postValue(repos) // update the LiveData to show it
            }
        }
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun loadUsername() {
        viewModelScope.launch {
            userPreferences.getUserData().collect {
                _username.postValue(it ?: "")
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            userPreferences.saveLoginState(false)
        }
    }

}
