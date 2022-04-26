package com.tv.maze.models

class Episode(
    val id: Long,
    val name: String,
    val season: Long,
    val summary: String? = null,
    val image: Poster? = null
)