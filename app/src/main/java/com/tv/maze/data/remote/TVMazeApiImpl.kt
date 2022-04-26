package com.tv.maze.data.remote

import com.tv.maze.data.models.Show
import retrofit2.Response
import javax.inject.Inject

class TVMazeApiImpl @Inject constructor(
    private val apiService: TVMazeApiService
) : TVMazeApi {

    override suspend fun getShows(page: Int): Response<ArrayList<Show>> =
        apiService.getShows(page)
}