package com.tv.maze.data.remote

import com.tv.maze.models.Episode
import retrofit2.Response
import javax.inject.Inject

class TVMazeApiImpl @Inject constructor(
    private val apiService: TVMazeApiService
) : TVMazeApi {

    override suspend fun getAllShows(): Response<ArrayList<Episode>> = apiService.getAllShows()
}