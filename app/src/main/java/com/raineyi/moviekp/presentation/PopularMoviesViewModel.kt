package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import kotlinx.coroutines.launch

class PopularMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private var page = 1
    private val movieDao = MovieDatabase.getInstance(application).moviesDao()

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>>
        get() = _listOfMovies

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        loadMovies()
    }

//    fun changeState(movieDto: MovieDto) {
//        val newMovie = movieDto.copy(isFavourite = !movieDto.isFavourite)
//        editShopItemUseCase.editShopItem(newItem)
//    }

    fun insertMovie(movieDto: MovieDto) {
        movieDto.isFavourite = true
        viewModelScope.launch {
            val mapper = MovieMapper()
            try {
                movieDao.insertMovieToDb(mapper.mapDtoToDbModel(movieDto))
            } catch (e: Exception) {
                throw RuntimeException("Can't insert movie: ${e.message}")
            }

            try {
                val descriptionMapper = DescriptionMapper()
                val description = ApiFactory.apiService.getDescription(movieDto.movieId)
                description.movieId = movieDto.movieId
                movieDao.insertDescription(descriptionMapper.mapDtoToDbModel(description))
            } catch (e: Exception) {
                throw RuntimeException("Can't insert description: ${e.message}")
            }
        }
    }

    fun removeMovie(movieDto: MovieDto) {
        movieDto.isFavourite = false
        viewModelScope.launch {
            try {
                movieDao.removeMovie(movieDto.movieId)
            } catch (e: Exception) {
                throw RuntimeException("Can't remove movie: ${e.message}")
            }

            try {
                movieDao.removeMovieDescription(movieDto.movieId)
            } catch (e: Exception) {
                throw RuntimeException("Can't removed description: ${e.message}")
            }
        }
    }


    fun loadMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
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
