package com.practicum.playlistmaker.media.ui.fragments

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.media.domain.PlaylistsInteractor

class PlaylistsViewModel (private val playlistsInteractor: PlaylistsInteractor) : ViewModel() {
    fun newPlaylist() {
        playlistsInteractor.newPlayList()
    }
}
