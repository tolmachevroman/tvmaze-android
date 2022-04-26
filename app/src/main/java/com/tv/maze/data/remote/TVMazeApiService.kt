package com.tv.maze.data.remote

import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeApiService {

    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): Response<ArrayList<Show>>

    @GET("shows/{showId}/seasons")
    suspend fun getSeasonsByShow(@Path("showId") showId: Int): Response<ArrayList<Season>>
}