package com.practicum.playlistmaker.search.data.network.impl

import com.practicum.playlistmaker.search.data.dto.Response
import com.practicum.playlistmaker.search.data.dto.TracksRequest
import com.practicum.playlistmaker.search.data.network.ITunesSearchApi
import com.practicum.playlistmaker.search.data.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ITunesNetworkClient(private val iTunesService: ITunesSearchApi) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return if (dto is TracksRequest) {
            withContext(Dispatchers.IO) {
                val response = iTunesService.search(dto.expression)
                response.apply { resultCode = HTTP_OK }
            }
        } else {
            Response().apply { resultCode = HTTP_BAD_REQUEST }
        }
    }

    companion object {
        const val HTTP_OK = 200
        const val HTTP_BAD_REQUEST = 400
    }
}