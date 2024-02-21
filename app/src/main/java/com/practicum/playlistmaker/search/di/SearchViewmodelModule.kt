package com.practicum.playlistmaker.search.di

import com.practicum.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.practicum.playlistmaker.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(
            SearchInteractorImpl(
                trackRepository = get(),
                searchHistoryRepository = get()
            )
        )
    }
}