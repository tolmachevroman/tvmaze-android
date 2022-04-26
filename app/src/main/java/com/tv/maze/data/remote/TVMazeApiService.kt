package com.tv.maze.data.remote

import com.tv.maze.models.Episode
import retrofit2.Response
import retrofit2.http.GET

interface TVMazeApiService {

    @GET("schedule/full")
    suspend fun getAllShows(): Response<ArrayList<Episode>>
}