package com.tv.maze.data.models.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.tv.maze.data.models.Person

class PersonType : NavType<Person>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Person? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Person {
        return Gson().fromJson(value, Person::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Person) {
        bundle.putParcelable(key, value)
    }
}