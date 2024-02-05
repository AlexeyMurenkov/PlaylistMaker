package com.practicum.playlistmaker.main.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.main.domain.MainInteractor
import com.practicum.playlistmaker.settings.domain.SettingsInteractor
import com.practicum.playlistmaker.utils.Creator
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

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mainInteractor = Creator.provideMainInteractor(context)
                val settingsInteractor = Creator.provideSettingsInteractor(context)

                MainViewModel(mainInteractor, settingsInteractor)
            }
        }
    }
}