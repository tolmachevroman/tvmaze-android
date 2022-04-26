package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: Int,
    val name: String,
    val genres: ArrayList<String>,
    val image: Poster?,
    val summary: String,
    val schedule: Schedule
) : Parcelable