package com.practicum.playlistmaker.main.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.main.domain.MainInteractor
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.utils.Creator
import com.practicum.playlistmaker.utils.switchTheme

class MainViewModel(
    application: Application,
    val mainInteractor: MainInteractor,
    settingsInteractor: SettingsInteractor
) :
    AndroidViewModel(application) {
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

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val mainInteractor = Creator.provideMainInteractor(context)
                val settingsInteractor = Creator.provideSettingsInteractor(context)

                MainViewModel(application, mainInteractor, settingsInteractor)
            }
        }
    }
}