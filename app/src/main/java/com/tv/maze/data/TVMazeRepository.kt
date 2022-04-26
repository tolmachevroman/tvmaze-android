package com.tv.maze.data

import com.tv.maze.data.remote.TVMazeApiImpl
import javax.inject.Inject

class TVMazeRepository @Inject constructor(
    private val tvMazeApiImpl: TVMazeApiImpl
) {
    suspend fun getAllShows() = tvMazeApiImpl.getAllShows()
}