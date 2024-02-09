package com.practicum.playlistmaker

import android.app.Application
import com.practicum.playlistmaker.main.di.mainDataModule
import com.practicum.playlistmaker.main.di.mainViewModelModule
import com.practicum.playlistmaker.player.di.playerDataModule
import com.practicum.playlistmaker.player.di.playerDomainModule
import com.practicum.playlistmaker.player.di.playerViewModelModule
import com.practicum.playlistmaker.search.di.searchDataModule
import com.practicum.playlistmaker.search.di.searchViewModelModule
import com.practicum.playlistmaker.settings.di.settingsDataModule
import com.practicum.playlistmaker.settings.di.settingsViewModelModule
import com.practicum.playlistmaker.utils.di.utilsDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlaylistMakerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlaylistMakerApplication)
            modules(
                searchViewModelModule, searchDataModule,
                settingsViewModelModule, settingsDataModule,
                mainViewModelModule, mainDataModule,
                playerViewModelModule, playerDomainModule, playerDataModule,
                utilsDataModule
            )
        }
    }
}
