package com.practicum.playlistmaker.player.domain.models

sealed class PlayerScreenState {
    class Progress(val state: PlayerState, val position: Int): PlayerScreenState()
    class Favorite(val isFavorite: Boolean): PlayerScreenState()
}

