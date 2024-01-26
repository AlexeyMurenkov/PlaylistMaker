package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.NetworkClient
import com.practicum.playlistmaker.data.dto.Response
import com.practicum.playlistmaker.data.dto.TracksRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ITunesNetworkClient : NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)

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

        private const val BASE_URL = "https://itunes.apple.com"
    }
}