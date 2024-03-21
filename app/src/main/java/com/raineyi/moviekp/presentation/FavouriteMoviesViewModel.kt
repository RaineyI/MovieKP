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
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = MovieDatabase.getInstance(application).moviesDao()

    fun getFavouriteMovies(): LiveData<List<MovieDto>> {
        val mapper = MovieMapper()
        return movieDao.getAllFavouriteMovies()
            .map { it -> it.map { mapper.mapDbModelToMovieDto(it) } }
    }

//    fun insertMovie(movieDto: MovieDto) {
//        movieDto.isFavourite = true
//        viewModelScope.launch {
//            val mapper = MovieMapper()
//            try {
//                movieDao.insertMovieToDb(mapper.mapDtoToDbModel(movieDto))
//            } catch (e: Exception) {throw RuntimeException("Can't insert movie: ${e.message}")}
//
//            try {
//                val descriptionMapper = DescriptionMapper()
//                val description = ApiFactory.apiService.getDescription(movieDto.movieId)
//                description.movieId = movieDto.movieId
//                movieDao.insertDescription(descriptionMapper.mapDtoToDbModel(description))
//            } catch (e: Exception) {throw RuntimeException("Can't insert description: ${e.message}")}
//        }
//    }

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

    fun getDbDescription(movieId: Int): LiveData<DescriptionDbModel> {
        return movieDao.getFavouriteMovieDescription(movieId)
    }

//    fun insertMovieDescription(movie: MovieDto) {
//        viewModelScope.launch {
//            try {
//                val mapper = DescriptionMapper()
//                val description = ApiFactory.apiService.getDescription(movie.movieId)
//                description.movieId = movie.movieId
//                movieDao.insertDescription(mapper.mapDtoToDbModel(description))
//            } catch (e: Exception) {throw RuntimeException("Can't insert description: ${e.message}")}
//        }
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
