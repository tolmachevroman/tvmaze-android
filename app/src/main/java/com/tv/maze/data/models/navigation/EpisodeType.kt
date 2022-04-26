package com.tv.maze.data.models.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.tv.maze.data.models.Episode

class EpisodeType : NavType<Episode>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Episode? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Episode {
        return Gson().fromJson(value, Episode::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Episode) {
        bundle.putParcelable(key, value)
    }
}