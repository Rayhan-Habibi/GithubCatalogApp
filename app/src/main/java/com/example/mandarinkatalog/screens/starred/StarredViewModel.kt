package com.example.mandarinkatalog.screens.starred

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mandarinkatalog.api.ResponseItem
import com.example.mandarinkatalog.dataStore.UserPreferences
import com.example.mandarinkatalog.database.UserHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _repos = MutableLiveData<List<ResponseItem>>()
    val repos: LiveData<List<ResponseItem>> = _repos



}