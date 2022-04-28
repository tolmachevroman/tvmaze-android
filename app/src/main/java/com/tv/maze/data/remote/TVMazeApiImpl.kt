package com.tv.maze.data.remote

import com.tv.maze.data.models.*
import retrofit2.Response
import javax.inject.Inject

class TVMazeApiImpl @Inject constructor(
    private val apiService: TVMazeApiService
) : TVMazeApi {

    override suspend fun searchShows(query: String): Response<ArrayList<ShowFound>> =
        apiService.searchShows(query)

    override suspend fun searchPeople(query: String): Response<ArrayList<PersonFound>> =
        apiService.searchPeople(query)

    override suspend fun getShows(page: Int): Response<ArrayList<Show>> =
        apiService.getShows(page)

    override suspend fun getShow(showId: Int): Response<Show> =
        apiService.getShow(showId)

    override suspend fun getSeasonsByShow(showId: Int): Response<ArrayList<Season>> =
        apiService.getSeasonsByShow(showId)

    override suspend fun getEpisodesBySeason(seasonId: Int): Response<ArrayList<Episode>> =
        apiService.getEpisodesBySeason(seasonId)

}