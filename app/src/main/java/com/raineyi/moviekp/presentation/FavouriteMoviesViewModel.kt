package com.raineyi.moviekp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.data.repository.MovieRepositoryImpl
import com.raineyi.moviekp.domain.GetDescriptionUseCase
import com.raineyi.moviekp.domain.GetMovieListUseCase
import com.raineyi.moviekp.domain.LoadMoviesUseCase
import com.raineyi.moviekp.domain.RemoveMovieFromDbUseCase
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepositoryImpl(application)
    private val getMovies = GetMovieListUseCase(repository)
    private val getDescription = GetDescriptionUseCase(repository)
    private val removeMovieFromDbUseCase = RemoveMovieFromDbUseCase(repository)


//    private val movieDao = MovieDatabase.getInstance(application).moviesDao()

    fun getFavouriteMovies(): LiveData<List<Movie>> {
     return getMovies()
    }


//    fun getFavouriteMovies(): LiveData<List<Movie>> {
//        val mapper = MovieMapper()
//        return movieDao.getAllFavouriteMovies()
//            .map { it -> it.map { mapper.mapDbModelToMovieDto(it) } }
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
        return getDescription(movieId)
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
