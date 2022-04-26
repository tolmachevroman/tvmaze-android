package com.tv.maze.data.remote

import com.tv.maze.data.models.Episode
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import retrofit2.Response

interface TVMazeApi {

    suspend fun getShows(page: Int): Response<ArrayList<Show>>

    suspend fun getSeasonsByShow(showId: Int): Response<ArrayList<Season>>

    suspend fun getEpisodesBySeason(seasonId: Int): Response<ArrayList<Episode>>
}