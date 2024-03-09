package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var page = 1
    private val movieDao = MovieDatabase.getInstance(application).moviesDao()

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>>
        get() = _listOfMovies

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

//    private var _listOfFavouriteMovie = MutableLiveData<List<MovieDto>>()
//    val listOfFavouriteMovie: LiveData<List<MovieDto>>
//        get() = _listOfFavouriteMovie

    init {
        loadMovies()
    }

    fun getFavouriteMovie(movieId: Int): LiveData<MovieDbModel> {
        return movieDao.getFavouriteMovie(movieId)
    }

    fun insertMovie(movieDto: MovieDto) {
        viewModelScope.launch {
            val mapper = MovieMapper()
            movieDao.addMovieToDb(mapper.mapDtoToDbModel(movieDto))
            //TODO: Обработка неуспешного сохранения в DB
        }
    }

    fun removeMovie(movieId: Int) {
        viewModelScope.launch {
            movieDao.removeMovie(movieId)
        }
    }

    fun insertMovieDescription(movieId: Int) {
        viewModelScope.launch {
            val mapper = DescriptionMapper()
            val description = ApiFactory.apiService.getDescription(movieId)
            movieDao.addDescription(mapper.mapDtoToDbModel(description))
            //TODO: Обработка неуспешного сохранения в DB
        }
    }

    fun removeMovieDescription(movieId: Int) {
        viewModelScope.launch {
            movieDao.removeMovieDescription(movieId)
        }
    }

    fun loadMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
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
