package com.tv.maze.data.models

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ShowType : NavType<Show>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Show? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Show {
        return Gson().fromJson(value, Show::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Show) {
        bundle.putParcelable(key, value)
    }
}