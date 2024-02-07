package com.practicum.playlistmaker.search.data.network.impl

import com.practicum.playlistmaker.search.data.dto.Response
import com.practicum.playlistmaker.search.data.dto.TracksRequest
import com.practicum.playlistmaker.search.data.network.ITunesSearchApi
import com.practicum.playlistmaker.search.data.network.NetworkClient

class ITunesNetworkClient(private val iTunesService: ITunesSearchApi) : NetworkClient {
    override fun doRequest(dto: Any): Response {
        return if (dto is TracksRequest) {
            val response = iTunesService.search(dto.expression).execute()
            val body = response.body() ?: Response()
            body.apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = HTTP_BAD_REQUEST }
        }
    }

    companion object {
        const val HTTP_OK = 200
        const val HTTP_BAD_REQUEST = 400
    }
}