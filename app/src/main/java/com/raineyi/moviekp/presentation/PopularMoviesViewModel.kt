package com.raineyi.moviekp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDao
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
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
    private val movieDao: MovieDao,
) : ViewModel() {


    private var page = 1

    private var _listOfMovies = MutableLiveData<List<Movie>>()
    val listOfMovies: LiveData<List<Movie>>
        get() = _listOfMovies

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _description = MutableLiveData<Description?>()
    val description: LiveData<Description?>
        get() = _description

    init {
        loadMovies()
//        viewModelScope.launch {
//            loadMoviesUseCase(page)
//        }
    }

    fun insertMovie(movie: Movie) {
        movie.isFavourite = true
        viewModelScope.launch {
            val mapper = MovieMapper()
            try {
                movieDao.insertMovieToDb(mapper.mapMovieToMovieDbModel(movie))
            } catch (e: Exception) {
                throw RuntimeException("Can't insert movie: ${e.message}")
            }

            try {
                val descriptionMapper = DescriptionMapper()
                val description = ApiFactory.apiService.getDescription(movie.movieId)
                description.movieId = movie.movieId
                movieDao.insertDescription(descriptionMapper.mapDtoToDbModel(description))
            } catch (e: Exception) {
                throw RuntimeException("Can't insert description: ${e.message}")
            }
        }
    }

//    fun insertMovie(movie: Movie, description: Description) {
//        movie.isFavourite = true
//        //TODO: ???
//        viewModelScope.launch {
//            insertMovieToDbUseCase(movie, description)
//        }
//    }

    fun removeMovie(movie: Movie) {
        movie.isFavourite = false
        viewModelScope.launch {
            try {
                movieDao.removeMovie(movie.movieId)
            } catch (e: Exception) {
                throw RuntimeException("Can't remove movie: ${e.message}")
            }

            try {
                movieDao.removeMovieDescription(movie.movieId)
            } catch (e: Exception) {
                throw RuntimeException("Can't removed description: ${e.message}")
            }
        }
    }

//
//    fun removeMovie(movie: Movie, description: Description) {
//        movie.isFavourite = false
//        //TODO: ???
//        viewModelScope.launch {
//            removeMovieFromDbUseCase(movie, description)
//        }
//    }

    fun loadDescription(movie: Movie) {
        viewModelScope.launch {
            try {
                movie.movieId.let {
                    val loadingDescription = loadDescriptionUseCase(movie.movieId)
                    _description.value = loadingDescription
                }
            } catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
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
                val movies = loadMoviesUseCase(page = page)
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


//    fun changeState(movieDto: MovieDto) {
//        val newMovie = movieDto.copy(isFavourite = !movieDto.isFavourite)
//        editShopItemUseCase.editShopItem(newItem)
//    }



//    fun loadMovies() {
//        val loading = isLoading.value
//        if (loading != null && loading) {
//            return
//        }
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val movieResponse = ApiFactory.apiService.getMovieResponse(page = page)
//                val movies = movieResponse.movies
//                //TODO:  let + let && delay()
//                val loadedMovies = _listOfMovies.value?.toMutableList()
//                if (loadedMovies != null) {
//                    movies?.let {
//                        loadedMovies.addAll(it)
//                        loadedMovies.let {
//                            _listOfMovies.value = it
//                        }
//                    }
//                } else {
//                    movies?.let {
//                        _listOfMovies.value = it
//                    }
//                }
//                Log.d("TEST_API", "Loaded page: $page")
//                page++
//            } catch (e: Exception) {
//                Log.d("TEST_API", e.message.toString())
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
        }
    }
}
