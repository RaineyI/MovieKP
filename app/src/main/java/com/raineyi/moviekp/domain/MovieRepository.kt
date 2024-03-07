package com.raineyi.moviekp.domain

import androidx.lifecycle.LiveData
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie

interface MovieRepository {

    fun getMovieList(): LiveData<List<Movie>>

    fun getMovie(movieId: Int): LiveData<Movie>

    fun getDescription(movieId: Int): LiveData<Description>

    suspend fun loadMovies(page: Int)

    suspend fun loadDescription(movieId: Int)
}