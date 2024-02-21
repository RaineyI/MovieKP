package com.raineyi.moviekp.domain

import androidx.lifecycle.LiveData
import com.raineyi.moviekp.data.model.DescriptionResponse

interface MovieRepository {

    fun getMovieList(): LiveData<List<Movie>>

    fun getDescription(movieId: Int): LiveData<DescriptionResponse>
}