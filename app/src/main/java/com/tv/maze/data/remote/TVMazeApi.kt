package com.tv.maze.data.remote

import com.tv.maze.models.Episode
import retrofit2.Response

interface TVMazeApi {

    suspend fun getAllShows(): Response<ArrayList<Episode>>
}