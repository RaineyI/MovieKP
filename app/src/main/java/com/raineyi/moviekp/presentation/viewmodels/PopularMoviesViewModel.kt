package com.raineyi.moviekp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.domain.GetFavouriteMovieUseCase
import com.raineyi.moviekp.domain.GetMovieListUseCase
import com.raineyi.moviekp.domain.InsertMovieToDbUseCase
import com.raineyi.moviekp.domain.LoadDescriptionUseCase
import com.raineyi.moviekp.domain.LoadMoviesUseCase
import com.raineyi.moviekp.domain.RemoveMovieFromDbUseCase
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val loadDescriptionUseCase: LoadDescriptionUseCase,
    private val insertMovieToDbUseCase: InsertMovieToDbUseCase,
    private val removeMovieFromDbUseCase: RemoveMovieFromDbUseCase,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase,
    private val getMovieListUseCase: GetMovieListUseCase,
) : ViewModel() {


    private var page = 1

    private var _listOfPopularMovies = MutableLiveData<List<Movie>>()
    val listOfPopularMovies: LiveData<List<Movie>>
        get() = _listOfPopularMovies

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val getFavouriteMovieList = getMovieListUseCase()

    init {
        loadMovies()
    }

    fun getFavouriteMovie(movieId: Int): LiveData<Movie?> {
        return getFavouriteMovieUseCase(movieId)
    }

    fun insertMovie(movie: Movie, description: Description) {
        viewModelScope.launch {
            insertMovieToDbUseCase(movie, description)
        }
    }

    fun removeMovie(movie: Movie) {
        viewModelScope.launch {
            removeMovieFromDbUseCase(movie)
        }
    }

    fun loadDescription(movie: Movie): LiveData<Description?> {
        val loadedDescription = MutableLiveData<Description?>()

        viewModelScope.launch {
            try {
                movie.movieId.let {
                    val loadingDescription = loadDescriptionUseCase(movie.movieId)
                    loadedDescription.value = loadingDescription
                }
            } catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
            }
        }
        return loadedDescription
    }


    fun loadMovies() {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val movies = loadMoviesUseCase(page = page)
                val loadedMovies = _listOfPopularMovies.value?.toMutableList()
                if (loadedMovies != null) {
                    movies?.let {
                        loadedMovies.addAll(it)
                        loadedMovies.let {
                            _listOfPopularMovies.value = it
                        }
                    }
                } else {
                    movies?.let {
                        _listOfPopularMovies.value = it

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
