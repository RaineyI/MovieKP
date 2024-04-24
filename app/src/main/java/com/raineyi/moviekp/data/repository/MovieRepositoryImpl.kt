package com.raineyi.moviekp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.raineyi.moviekp.data.database.MovieDao
import com.raineyi.moviekp.data.mapper.DescriptionMapper
import com.raineyi.moviekp.data.mapper.MovieMapper
import com.raineyi.moviekp.data.network.ApiService
import com.raineyi.moviekp.domain.MovieRepository
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesMapper: MovieMapper,
    private val descriptionMapper: DescriptionMapper,
    private val movieDao: MovieDao,
    private val apiService: ApiService
) : MovieRepository {

    override fun getMovieList(): LiveData<List<Movie>> {
        return movieDao.getAllFavouriteMovies().map {
            it.map { movieDbModel ->
                moviesMapper.mapDbModelToMovie(movieDbModel)
            }
        }
    }

    override fun getFavouriteMovie(movieId: Int): LiveData<Movie?> {
        return movieDao.getFavouriteMovie(movieId).map {
            moviesMapper.mapNullableDbModelToMovie(it)
        }
    }

    override fun getDescription(movieId: Int): LiveData<Description> {
        return movieDao.getFavouriteMovieDescription(movieId).map {
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
        movie.isFavourite = true
        description.movieId = movie.movieId
        try {
            movieDao.insertMovieToDb(moviesMapper.mapMovieToMovieDbModel(movie))
//            Log.d("TEST_DB", "insert in repository ${movie.name}")
        } catch (e: Exception) {
            Log.d("TEST_DB", "Can't insert movie: ${e.message}")
        }
        try {
            movieDao.insertDescription(
                descriptionMapper.mapDescriptionToDescriptionDbModel(description)
            )
        } catch (e: Exception) {
            Log.d("TEST_DB", "Can't insert description: ${e.message}")
        }


//                (movie: Movie, description: Description) {
//        try {
//            val movieDbModel = moviesMapper.mapMovieToMovieDbModel(movie)
//            movieDao.insertMovieToDb(movieDbModel)
//        } catch (e: Exception) {
//            Log.d("TEST_DB", "Can't insert movie: ${e.message}")
//        }
//
//        try {
//            val descriptionDbModel =
//                descriptionMapper.mapDescriptionToDescriptionDbModel(description)
//            movieDao.insertDescription(descriptionDbModel)
//        } catch (e: Exception) {
//            Log.d("TEST_DB", "Can't insert description: ${e.message}")
//        }
    }

    override suspend fun removeMovieFromDb(movie: Movie) {
        try {
            movie.movieId.let { movieDao.removeMovie(it) }
//            Log.d("TEST_DB", "remove fom repository ${movie.name}")
        } catch (e: Exception) {
            Log.d("TEST_DB", "Can't remove movie: ${e.message}")
        }

        try {
            movie.movieId.let { movieDao.removeMovieDescription(it) }
        } catch (e: Exception) {
            Log.d("TEST_DB", "Can't remove description: ${e.message}")
        }
    }
}
