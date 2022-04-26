package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonFound(
    val person: Person
) : Parcelable