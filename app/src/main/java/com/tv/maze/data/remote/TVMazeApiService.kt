package com.tv.maze.data.remote

import com.tv.maze.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeApiService {

    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): Response<ArrayList<ShowFound>>

    @GET("search/people")
    suspend fun searchPeople(@Query("q") query: String): Response<ArrayList<PersonFound>>

    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): Response<ArrayList<Show>>

    @GET("shows/{showId}/seasons")
    suspend fun getSeasonsByShow(@Path("showId") showId: Int): Response<ArrayList<Season>>

    @GET("seasons/{seasonId}/episodes")
    suspend fun getEpisodesBySeason(@Path("seasonId") seasonId: Int): Response<ArrayList<Episode>>
}