package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var page = 1
    private val movieDao = MovieDatabase.getInstance(application).moviesDao()

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>>
        get() = _listOfMovies

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

//    private var _listOfFavouriteMovie = MutableLiveData<List<MovieDto>>()
//    val listOfFavouriteMovie: LiveData<List<MovieDto>>
//        get() = _listOfFavouriteMovie

    init {
        loadMovies()
    }

    fun loadMovies() {
        val loading = isLoading.value
        if(loading != null && loading) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
//            delay(100)
            try {
                val movieResponse = ApiFactory.apiService.getMovieResponse(page = page)
                val movies = movieResponse.movies
                //TODO:  let + let && delay()
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
                Log.d("TEST_API", "Loaded page: $page")
                page++
            } catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}
