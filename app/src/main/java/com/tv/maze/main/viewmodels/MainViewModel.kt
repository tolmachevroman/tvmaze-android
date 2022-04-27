package com.tv.maze.main.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.data.TVMazeRepository
import com.tv.maze.data.models.Person
import com.tv.maze.data.models.Season
import com.tv.maze.data.models.Show
import com.tv.maze.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences,
    private val tvMazeRepository: TVMazeRepository
) : ViewModel() {

    var shows by mutableStateOf<Resource<List<Show>>>(Resource.loading(null))
    var seasonsByShow by mutableStateOf<Resource<ArrayList<Season>>>(Resource.loading(null))
    var people by mutableStateOf<Resource<List<Person>>>(Resource.loading(null))

    //TODO implement pagination
    private var currentPage = 0

    fun getAllShows() {
        viewModelScope.launch {
            tvMazeRepository.getShows(currentPage).let { result ->
                shows = result
            }
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
                        Resource.success(showsFound.map { it.show })
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
                        Resource.success(peopleFound.map { it.person })
                    } else {
                        Resource.error(response.message(), null)
                    }

                }
            }
        }
    }
}