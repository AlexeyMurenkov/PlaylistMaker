package com.practicum.playlistmaker.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.SharingInteractor

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private val stateIsDarkTheme = MutableLiveData<Boolean>()
    fun getStateIsDarkTheme(): LiveData<Boolean> = stateIsDarkTheme

    init {
        stateIsDarkTheme.value = settingsInteractor.isDarkMode
    }

    fun switchTheme(isDarkTheme: Boolean) {
        settingsInteractor.isDarkMode = isDarkTheme
        stateIsDarkTheme.value = isDarkTheme
        com.practicum.playlistmaker.utils.switchTheme(isDarkTheme)
    }

    fun shareApp() {
        sharingInteractor.shareApp()
    }

    fun showAgreement() {
        sharingInteractor.openTerms()
    }

    fun mailToSupport() {
        sharingInteractor.openSupport()
    }
}
