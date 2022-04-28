package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    val name: String,
    val gender: String?,
    val image: Poster?,
    val _embedded: CastCreditsWrapped? = null
) : Parcelable