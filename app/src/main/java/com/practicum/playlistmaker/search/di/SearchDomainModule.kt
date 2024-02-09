package com.practicum.playlistmaker.search.di

import com.practicum.playlistmaker.search.domain.SearchInteractor
import com.practicum.playlistmaker.search.domain.impl.SearchInteractorImpl
import org.koin.dsl.module

val searchDomainModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(trackRepository = get(), searchHistoryRepository = get())
    }
}