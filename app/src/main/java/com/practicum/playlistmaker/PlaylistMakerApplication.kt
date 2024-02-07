package com.practicum.playlistmaker

import android.app.Application
import com.practicum.playlistmaker.main.di.mainDataModule
import com.practicum.playlistmaker.main.di.mainDomainModule
import com.practicum.playlistmaker.main.di.mainViewModelModule
import com.practicum.playlistmaker.player.di.playerDataModule
import com.practicum.playlistmaker.player.di.playerDomainModule
import com.practicum.playlistmaker.player.di.playerViewModelModule
import com.practicum.playlistmaker.search.di.searchDataModule
import com.practicum.playlistmaker.search.di.searchDomainModule
import com.practicum.playlistmaker.search.di.searchViewModelModule
import com.practicum.playlistmaker.settings.di.settingsDataModule
import com.practicum.playlistmaker.settings.di.settingsDomainModule
import com.practicum.playlistmaker.settings.di.settingsViewModelModule
import com.practicum.playlistmaker.sharing.di.sharingDataModule
import com.practicum.playlistmaker.sharing.di.sharingDomainModule
import com.practicum.playlistmaker.utils.di.utilsDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlaylistMakerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlaylistMakerApplication)
            modules(
                sharingDomainModule, sharingDataModule,
                searchViewModelModule, searchDomainModule, searchDataModule,
                settingsViewModelModule, settingsDomainModule, settingsDataModule,
                mainViewModelModule, mainDomainModule, mainDataModule,
                playerViewModelModule, playerDomainModule, playerDataModule,
                utilsDataModule
            )
        }
    }
}
