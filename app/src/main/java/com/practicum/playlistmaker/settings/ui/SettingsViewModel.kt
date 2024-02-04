package com.practicum.playlistmaker.settings.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.utils.Creator
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.sharing.domain.SharingInteractor


class SettingsViewModel(
    application: Application,
    val settingsInteractor: SettingsInteractor,
    val sharingInteractor: SharingInteractor
) : AndroidViewModel(application) {

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

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                val settingsInteractor =
                    Creator.provideSettingsInteractor(context)
                val sharingInteractor =
                    Creator.provideSharingInteractor(context)

                SettingsViewModel(application, settingsInteractor, sharingInteractor)
            }
        }
    }
}
