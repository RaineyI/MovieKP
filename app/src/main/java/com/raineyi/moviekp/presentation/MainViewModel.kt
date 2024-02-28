package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var page = 1

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>> = _listOfMovies

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMovies() {
        val loading = isLoading.value
        if(loading != null && loading) {
            return
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val movieResponse = ApiFactory.apiService.getMovieResponse(page = page)
                val movies = movieResponse.blockingGet().movies
                //TODO: blockingGet() && let + let
                val loadedMovies = _listOfMovies.value?.toMutableList()
                if (loadedMovies != null) {
                    movies?.let {
                        loadedMovies.addAll(it)
                        loadedMovies.let {
                            _listOfMovies.value = it
                        }
                    }
                } else {
                    movies?.let {
                        _listOfMovies.value = it
                    }
                }
            } catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        page++
        getMovies()
    }
}
