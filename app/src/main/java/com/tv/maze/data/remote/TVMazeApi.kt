package com.tv.maze.data.remote

import com.tv.maze.data.models.Show
import retrofit2.Response

interface TVMazeApi {

    suspend fun getShows(page: Int): Response<ArrayList<Show>>
}