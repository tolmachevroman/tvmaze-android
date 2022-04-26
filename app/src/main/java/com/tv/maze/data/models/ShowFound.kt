package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowFound(
    val show: Show
) : Parcelable