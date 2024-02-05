package com.practicum.playlistmaker.player.domain.models

sealed class PlayerScreenState {
    class State(val state: PlayerState) : PlayerScreenState()
    class Position(val position: Int) : PlayerScreenState()
}
