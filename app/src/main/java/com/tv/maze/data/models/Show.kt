package com.tv.maze.data.models

data class Show(
    val id: Int,
    val name: String,
    val genres: ArrayList<String>,
    val image: Poster?,
    val summary: String
)