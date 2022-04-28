package com.tv.maze.data.remote

import com.tv.maze.data.models.*
import retrofit2.Response

interface TVMazeApi {

    suspend fun searchShows(query: String): Response<ArrayList<ShowFound>>

    suspend fun searchPeople(query: String): Response<ArrayList<PersonFound>>

    suspend fun getShows(page: Int): Response<ArrayList<Show>>

    suspend fun getShow(showId: Int): Response<Show>

    suspend fun getSeasonsByShow(showId: Int): Response<ArrayList<Season>>

    suspend fun getEpisodesBySeason(seasonId: Int): Response<ArrayList<Episode>>
}