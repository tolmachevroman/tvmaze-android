package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Season(
    val id: Int,
    val number: Int,
    var episodes: ArrayList<Episode>? = arrayListOf()
) : Parcelable