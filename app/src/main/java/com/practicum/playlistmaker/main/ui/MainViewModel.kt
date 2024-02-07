package com.practicum.playlistmaker.main.ui

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.main.domain.MainInteractor
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.utils.switchTheme

class MainViewModel(
    private val mainInteractor: MainInteractor,
    settingsInteractor: SettingsInteractor
) : ViewModel() {
    init {
        switchTheme(settingsInteractor.isDarkMode)
    }

    fun openSearchActivity() {
        mainInteractor.openSearchActivity()
    }

    fun openMediaActivity() {
        mainInteractor.openMediaActivity()
    }

    fun openSettingsActivity() {
        mainInteractor.openSettingsActivity()
    }
}