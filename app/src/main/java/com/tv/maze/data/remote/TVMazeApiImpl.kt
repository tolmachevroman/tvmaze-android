package com.tv.maze.data.remote

import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.FoundShow
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import retrofit2.Response
import javax.inject.Inject

class TVMazeApiImpl @Inject constructor(
    private val apiService: TVMazeApiService
) : TVMazeApi {

    override suspend fun searchShows(query: String): Response<ArrayList<FoundShow>> =
        apiService.searchShows(query)

    override suspend fun getShows(page: Int): Response<ArrayList<Show>> =
        apiService.getShows(page)

    override suspend fun getSeasonsByShow(showId: Int): Response<ArrayList<Season>> =
        apiService.getSeasonsByShow(showId)

    override suspend fun getEpisodesBySeason(seasonId: Int): Response<ArrayList<Episode>> =
        apiService.getEpisodesBySeason(seasonId)

}