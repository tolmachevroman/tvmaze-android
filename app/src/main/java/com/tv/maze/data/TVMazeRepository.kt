package com.tv.maze.data

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tv.maze.R
import com.tv.maze.data.models.Show
import com.tv.maze.data.remote.TVMazeApiImpl
import com.tv.maze.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class TVMazeRepository @Inject constructor(
    private val context: Context,
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences,
    private val tvMazeApiImpl: TVMazeApiImpl
) {
    suspend fun searchShows(query: String) = tvMazeApiImpl.searchShows(query)

    suspend fun searchPeople(query: String) = tvMazeApiImpl.searchPeople(query)

    suspend fun getShows(page: Int): Resource<ArrayList<Show>> {
        // check the last time we asked for these shows,
        // if less then 24 hours and page is less then the last one read,
        // then read them from the local file
        val lastTimestamp = sharedPreferences.getLong(
            resources.getString(R.string.key_last_get_shows_timestamp),
            0
        )
        val lastPage = sharedPreferences.getInt(
            resources.getString(R.string.key_last_get_shows_page),
            0
        )
        var result: Resource<ArrayList<Show>>
        val filename = "shows.json"
        val millisecondsInOneDay = 8.64e+7

        if (System.currentTimeMillis() > (lastTimestamp + millisecondsInOneDay) || page > lastPage) {
            val response = tvMazeApiImpl.getShows(page)
            val shows = response.body()
            if (response.isSuccessful && shows != null) {
                // write new data to file
                withContext(Dispatchers.IO) {
                    context.openFileOutput(filename, Context.MODE_PRIVATE).use { output ->
                        output.write(Gson().toJson(shows).toByteArray())

                        with(sharedPreferences.edit()) {
                            putLong(
                                resources.getString(R.string.key_last_get_shows_timestamp),
                                System.currentTimeMillis()
                            )
                            putInt(resources.getString(R.string.key_last_get_shows_page), page)
                            apply()
                        }
                    }
                }
                result = Resource.success(response.body())
            } else {
                result = Resource.error(response.message(), null)
            }
        } else {
            // read data from file
            withContext(Dispatchers.IO) {
                context.openFileInput(filename).use { stream ->
                    val json = stream.bufferedReader().use {
                        it.readText()
                    }
                    val type: Type = object : TypeToken<ArrayList<Show>>() {}.type
                    val shows: ArrayList<Show> = Gson().fromJson(json, type)
                    result = Resource.success(shows)
                }
            }

        }
        return result
    }

    suspend fun getSeasonsByShow(showId: Int) = tvMazeApiImpl.getSeasonsByShow(showId)

    suspend fun getEpisodesBySeason(seasonId: Int) = tvMazeApiImpl.getEpisodesBySeason(seasonId)
}