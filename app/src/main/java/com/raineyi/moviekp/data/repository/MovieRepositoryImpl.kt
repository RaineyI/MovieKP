package com.raineyi.moviekp.data.repository

import android.app.Application
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
    private val mapper = MovieMapper()
    private val descriptionMapper = DescriptionMapper()
    private val apiService = ApiFactory.apiService
    override fun getMovieList(): LiveData<List<Movie>> {
        val movieList = moviesDao.getFavouriteMoviesList()
        return movieList.map {
            it.map { movieDbModel ->
                mapper.mapDbModelToMovie(movieDbModel)
            }
        }
    }

    override fun getMovie(movieId: Int): LiveData<Movie> {
        val movie = moviesDao.getFavouriteMovie(movieId)
        return movie.map{
            mapper.mapDbModelToMovie(it)
        }
    }

    override fun getDescription(movieId: Int): LiveData<Description> {
        val description = moviesDao.getFavouriteMovieDescription(movieId)
        return description.map {
            descriptionMapper.mapDbModelToDescription(it)
        }
    }

    override suspend fun loadMovies(page: Int): List<Movie>? {
        val movieResponseDto = ApiFactory.apiService.getMovieResponse(page = page)
        return movieResponseDto.movies?.map { mapper.mapDtoToMovie(it) }
        }

    override suspend fun loadDescription(movieId: Int): Description{
        val descriptionDto = ApiFactory.apiService.getDescription(movieId)
        return descriptionMapper.mapDtoToDescription(descriptionDto)
    }
}