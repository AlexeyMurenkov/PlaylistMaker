package com.practicum.playlistmaker.api

import com.practicum.playlistmaker.track.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchApi {
    @GET("search?entity=song") fun search(@Query("term") text: String) : Call<TracksResponse>
}