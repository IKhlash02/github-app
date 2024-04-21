package com.dicoding.github.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.github.data.SettingPreferences
import com.dicoding.github.data.dataStore
import com.dicoding.github.databinding.ActivitySettingsBinding
import com.dicoding.github.ui.viewmodels.SettingViewModel
import com.dicoding.github.ui.viewmodels.ViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) {isDarkModeActive : Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener{_: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)

        }
    }
}