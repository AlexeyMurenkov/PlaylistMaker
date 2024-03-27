package com.practicum.playlistmaker

import android.app.Application
import com.practicum.playlistmaker.media.di.mediaDataModule
import com.practicum.playlistmaker.media.di.mediaDomainModule
import com.practicum.playlistmaker.media.di.mediaViewModelModule
import com.practicum.playlistmaker.player.di.playerDataModule
import com.practicum.playlistmaker.player.di.playerDomainModule
import com.practicum.playlistmaker.player.di.playerViewModelModule
import com.practicum.playlistmaker.search.di.searchDataModule
import com.practicum.playlistmaker.search.di.searchViewModelModule
import com.practicum.playlistmaker.settings.di.settingsDataModule
import com.practicum.playlistmaker.settings.di.settingsViewModelModule
import com.practicum.playlistmaker.settings.domain.SettingsRepository
import com.practicum.playlistmaker.utils.di.utilsDataModule
import com.practicum.playlistmaker.utils.switchTheme
import org.koin.android.ext.android.getKoin
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
                playerViewModelModule, playerDomainModule, playerDataModule,
                mediaViewModelModule, mediaDomainModule, mediaDataModule,
                utilsDataModule
            )
        }

        switchTheme(getKoin().get<SettingsRepository>().aplicationSettings.isDarkMode)
    }
}
