package com.tv.maze.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastCreditsWrapped(
    val castcredits: ArrayList<CastCredits>
) : Parcelable

@Parcelize
data class CastCredits(val _links: ShowLinkWrapped) : Parcelable

@Parcelize
data class ShowLinkWrapped(val show: ShowHrefWrapped) : Parcelable

@Parcelize
data class ShowHrefWrapped(val href: String) : Parcelable