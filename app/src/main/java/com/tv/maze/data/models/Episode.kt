package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Episode(
    val id: Long,
    val name: String,
    val season: Long,
    val number: Int,
    val summary: String,
    val image: Poster? = null
) : Parcelable