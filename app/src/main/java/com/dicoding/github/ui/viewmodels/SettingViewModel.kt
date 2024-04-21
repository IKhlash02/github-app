package com.dicoding.github.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.github.data.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences) : ViewModel(){

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }
}