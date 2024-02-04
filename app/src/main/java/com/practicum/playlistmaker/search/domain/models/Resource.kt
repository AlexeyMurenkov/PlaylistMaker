package com.practicum.playlistmaker.search.domain.models

sealed class Resource<T> {
    class Success<T>(val data: T): Resource<T>()
    class Error<T>: Resource<T>()
}
