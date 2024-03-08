package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val application: Application,
    private val movie: MovieDto
) : AndroidViewModel(application) {

    private var _description = MutableLiveData<DescriptionDto>() // || String?
    val description: LiveData<DescriptionDto>
        get() = _description

    init {
        loadDescription()
    }

    private fun loadDescription() {
        viewModelScope.launch {
            try {
                movie.movieId.let {
                    val loadingDescription = ApiFactory.apiService.getDescription(it)
                    _description.value = loadingDescription
                }
            }catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
            }
        }
    }
}