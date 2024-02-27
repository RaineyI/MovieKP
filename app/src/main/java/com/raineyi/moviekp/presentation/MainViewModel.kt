package com.raineyi.moviekp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var currentPage = 1

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>>
        get() = _listOfMovies

    fun getMovies() {
        viewModelScope.launch {
            val movieResponse = ApiFactory.apiService.getMovieResponse(page = currentPage)
            val movies = movieResponse.blockingGet().movies

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
        }
    }
    fun loadNextPage() {
        currentPage++
        getMovies()
    }
}
