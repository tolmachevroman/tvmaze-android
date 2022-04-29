package com.tv.maze.ui.authentication.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.maze.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    // For simplicity sake, it's the only validity check
    private val minPinLength = 4

    private var pin = ""

    var error by mutableStateOf<String?>(null)
    var isPinEmpty by mutableStateOf(true)

    private val _createdPin = MutableLiveData<Boolean?>(null)
    val createdPin: LiveData<Boolean?> get() = _createdPin

    fun onResetPin() {
        error = null
        isPinEmpty = true
    }

    fun onPinChange(newPin: String) {
        isPinEmpty = newPin.isEmpty()

        if (newPin.length < minPinLength) {
            error = resources.getString(R.string.minimum_length_for_pin)
        } else {
            error = null
            pin = newPin
        }
    }

    fun onLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            with(sharedPreferences) {
                val savedPin = getString(resources.getString(R.string.key_pin), null)
                _createdPin.postValue(savedPin == pin)
            }
        }
    }

    fun onCreate() {
        if (error == null) {
            viewModelScope.launch(Dispatchers.IO) {
                _createdPin.postValue(true)
                with(sharedPreferences.edit()) {
                    putString(
                        resources.getString(R.string.key_pin), pin
                    )
                    apply()
                }
            }
        }
    }
}


