package com.tv.maze.main.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.data.TVMazeRepository
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

//    private val _shows = MutableLiveData<Resource<ArrayList<Show>>>()
//    val shows: LiveData<Resource<ArrayList<Show>>>
//        get() = _shows

    var shows by mutableStateOf<Resource<ArrayList<Show>>>(Resource.loading(null))

    fun getAllShows() {
//        shows.postValue(Resource.loading(null))
        viewModelScope.launch {
            tvMazeRepository.getShows(1).let { response ->
                if (response.isSuccessful) {
                    shows = Resource.success(response.body())
//                    _shows.postValue(Resource.success(response.body()))
                } else {
                    //TODO
                }
            }
        }
    }
}