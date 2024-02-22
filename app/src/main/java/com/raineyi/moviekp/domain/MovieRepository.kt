package com.raineyi.moviekp.domain

import androidx.lifecycle.LiveData
import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie

interface MovieRepository {

    fun getMovieList(): LiveData<List<Movie>>

    fun getDescription(movieId: Int): LiveData<Description>
}