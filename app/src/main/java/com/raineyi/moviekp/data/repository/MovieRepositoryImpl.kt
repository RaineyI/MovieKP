package com.raineyi.moviekp.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.domain.MovieRepository
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie

class MovieRepositoryImpl(
    private val application: Application
) : MovieRepository {

    private val moviesDao = MovieDatabase.getInstance(application).moviesDao()
    private val moviesMapper = MovieMapper()
    private val descriptionMapper = DescriptionMapper()
    private val apiService = ApiFactory.apiService


    override fun getMovieList(): LiveData<List<Movie>> {
        return moviesDao.getAllFavouriteMovies().map {
            it.map { movieDbModel ->
                moviesMapper.mapDbModelToMovie(movieDbModel)
            }
        }
    }

    override fun getDescription(movieId: Int): LiveData<Description> {
        return moviesDao.getFavouriteMovieDescription(movieId).map {
            descriptionMapper.mapDbModelToDescription(it)
        }
    }

    override suspend fun loadMovies(page: Int): List<Movie>? {
        return try {
            val movieResponseDto = apiService.getMovieResponse(page = page)
            movieResponseDto.movies?.map { moviesMapper.mapDtoToMovie(it) }
        } catch (e: Exception) {
            Log.d("TEST_API", e.message.toString())
            null
        }
    }

    override suspend fun loadDescription(movieId: Int): Description? {
        return try {
            val descriptionDto = apiService.getDescription(movieId)
             descriptionMapper.mapDtoToDescription(descriptionDto)
        } catch (e: Exception) {
            Log.d("TEST_API", e.message.toString())
            null
        }
    }

    override suspend fun insertMovieToDb(movie: Movie, description: Description) {
        try {
            val movieDbModel = moviesMapper.mapMovieToMovieDbModel(movie)
            moviesDao.insertMovieToDb(movieDbModel)
            val descriptionDbModel = descriptionMapper.mapDescriptionToDescriptionDbModel(description)
            moviesDao.insertDescription(descriptionDbModel)
        } catch (e: Exception) {
            Log.d("TEST_DB", e.message.toString())
        }
    }

    override suspend fun deleteMovieFromDb(movie: Movie, description: Description) {
        try {
            movie.movieId?.let { moviesDao.removeMovie(it) }
            movie.movieId?.let { moviesDao.removeMovieDescription(it) }
        } catch (e: Exception) {
            Log.d("TEST_DB", e.message.toString())
        }
    }
}