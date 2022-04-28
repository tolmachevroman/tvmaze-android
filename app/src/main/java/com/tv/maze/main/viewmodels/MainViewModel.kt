package com.tv.maze.main.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.R
import com.tv.maze.data.TVMazeRepository
import com.tv.maze.data.models.Person
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import com.tv.maze.utils.Resource
import com.tv.maze.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences,
    private val tvMazeRepository: TVMazeRepository
) : ViewModel() {

    var shows by mutableStateOf<Resource<ArrayList<Show>>>(Resource.loading(null))
    var favoriteShows by mutableStateOf<Resource<ArrayList<Show>>>(Resource.loading(null))
    var seasonsByShow by mutableStateOf<Resource<ArrayList<Season>>>(Resource.loading(null))
    var people by mutableStateOf<Resource<ArrayList<Person>>>(Resource.loading(null))

    private val favoriteShowsIds = mutableListOf<Int>()

    private var currentPage = 0

    init {
        getFavoriteShows()
        getShows()
    }

    private fun getShows() {
        viewModelScope.launch {
            tvMazeRepository.getShows(currentPage).let { result ->
                // if result is successful, add new shows to the list
                shows = if (result.status == Status.SUCCESS) {
                    val data: ArrayList<Show> = arrayListOf()
                    shows.data?.let { data.addAll(it) }
                    result.data?.let { data.addAll(it) }

                    Resource.success(data)
                } else {
                    result
                }

            }
        }
    }

    fun onLoadMoreShows() {
        currentPage += 1
        getShows()
    }

    private fun getShowsAndAddToFavorites(showIds: List<Int>) {
        viewModelScope.launch {
            val data: ArrayList<Show> = arrayListOf()
            favoriteShows.data?.let { data.addAll(it) }

            showIds.forEach { showId ->
                tvMazeRepository.getShow(showId).let { result ->
                    result.takeIf { it.isSuccessful }?.body()?.let { show -> data.add(show) }
                }
            }
            favoriteShows = Resource.success(data)
        }
    }

    fun getSeasonsByShow(showId: Int) {
        seasonsByShow = Resource.loading(null)
        viewModelScope.launch {
            tvMazeRepository.getSeasonsByShow(showId).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    val seasons = response.body()!!
                    seasons.forEach { season ->
                        val episodes = tvMazeRepository.getEpisodesBySeason(season.id)
                        if (episodes.isSuccessful) {
                            season.episodes = episodes.body()
                        }
                    }
                    seasonsByShow = Resource.success(seasons)
                } else {
                    seasonsByShow = Resource.error(response.message(), null)
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        if (query.isEmpty()) {
            viewModelScope.launch {
                shows = tvMazeRepository.getShows(0)
            }
        } else {
            // Search for shows
            shows = Resource.loading(null)
            viewModelScope.launch {
                tvMazeRepository.searchShows(query).let { response ->
                    val showsFound = response.body()
                    shows = if (response.isSuccessful && showsFound != null) {
                        val data: ArrayList<Show> = arrayListOf()
                        data.addAll(showsFound.map { it.show })
                        Resource.success(data)
                    } else {
                        Resource.error(response.message(), null)
                    }
                }
            }

            // Search for people
            people = Resource.loading(null)
            viewModelScope.launch {
                tvMazeRepository.searchPeople(query).let { response ->
                    val peopleFound = response.body()
                    people = if (response.isSuccessful && peopleFound != null) {
                        val data: ArrayList<Person> = arrayListOf()
                        data.addAll(peopleFound.map { it.person })
                        Resource.success(data)
                    } else {
                        Resource.error(response.message(), null)
                    }

                }
            }
        }
    }

    /**
     * Read comma-separated Show ids from SharedPreferences, load them from the API
     * and show in Favorites Tab
     */
    private fun getFavoriteShows() {
        favoriteShows = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            val showsEncoded =
                sharedPreferences.getString(resources.getString(R.string.key_favorite_shows), "")
            if (showsEncoded.isNullOrEmpty()) {
                viewModelScope.launch {
                    favoriteShows = Resource.error(
                        message = resources.getString(R.string.no_favorite_shows_yet),
                        null
                    )
                }
            } else {
                showsEncoded
                    .split(",")
                    .map { it.toInt() }
                    .let {
                        favoriteShowsIds.addAll(it)
                        getShowsAndAddToFavorites(favoriteShowsIds)
                    }
            }
        }
    }

    fun isShowFavorite(showId: Int): Boolean = favoriteShowsIds.contains(showId)

    /**
     * Add or remove Show by its id, and write comma-separated show ids to SharedPreferences
     */
    fun setFavorite(showId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteShowsIds.add(showId)
            getShowsAndAddToFavorites(listOf(showId))
        } else {
            favoriteShowsIds.remove(showId)
            favoriteShows.data?.removeAll { it.id == showId }

            if (favoriteShowsIds.isEmpty()) {
                favoriteShows = Resource.error(
                    message = resources.getString(R.string.no_favorite_shows_yet),
                    null
                )
            }
        }
        favoriteShowsIds.let {
            viewModelScope.launch(Dispatchers.IO) {
                with(sharedPreferences.edit()) {
                    putString(
                        resources.getString(R.string.key_favorite_shows),
                        it.joinToString(separator = ",")
                    )
                    apply()
                }
            }
        }
    }
}