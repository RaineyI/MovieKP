package com.raineyi.moviekp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDao
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.domain.GetDescriptionUseCase
import com.raineyi.moviekp.domain.GetMovieListUseCase
import com.raineyi.moviekp.domain.RemoveMovieFromDbUseCase
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val getDescriptionUseCase: GetDescriptionUseCase,
    private val removeMovieFromDbUseCase: RemoveMovieFromDbUseCase,
    private val movieDao: MovieDao,
) : ViewModel() {

    val getMovieList = getMovieListUseCase() //вылетает

    private var _listOfMovies = MutableLiveData<List<Movie>>()
    val listOfMovies: LiveData<List<Movie>>
        get() = _listOfMovies

//    private val movieDao = MovieDatabase.getInstance(application).moviesDao()


    init {
        getFavouriteMovies()
    }



    private fun getFavouriteMovies(){
      _listOfMovies.value = getMovieListUseCase().value
    }

//    fun getFavouriteMovies(): LiveData<List<Movie>> {
//        val mapper = MovieMapper()
//        return movieDao.getAllFavouriteMovies()
//            .map { it -> it.map { mapper.mapDbModelToMovie(it) } }
//    }

    fun removeMovie(movie: Movie, description: Description) {
        movie.isFavourite = false
        viewModelScope.launch {
            removeMovieFromDbUseCase(movie, description)
        }
    }

//    fun removeMovie(movieDto: MovieDto) {
//        movieDto.isFavourite = false
//        viewModelScope.launch {
//            try {
//                movieDao.removeMovie(movieDto.movieId)
//            } catch (e: Exception) {
//                throw RuntimeException("Can't remove movie: ${e.message}")
//            }
//
//            try {
//                movieDao.removeMovieDescription(movieDto.movieId)
//            } catch (e: Exception) {
//                throw RuntimeException("Can't removed description: ${e.message}")
//            }
//        }
//    }

    fun getDbDescription(movieId: Int): LiveData<Description> {
        return getDescriptionUseCase(movieId)
    }

//    fun getDbDescription(movieId: Int): LiveData<DescriptionDbModel> {
//        return movieDao.getFavouriteMovieDescription(movieId)
//    }


//    fun removeMovieDescription(movieId: Int) {
//        viewModelScope.launch {
//            try {
//                movieDao.removeMovieDescription(movieId)
//                movieDao.removeMovieDescription(0)
//            } catch (e: Exception) {throw RuntimeException("Can't removed description: ${e.message}")}
//        }
//    }
}
