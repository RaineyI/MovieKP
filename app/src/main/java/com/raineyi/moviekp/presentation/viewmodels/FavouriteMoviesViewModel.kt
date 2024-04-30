package com.raineyi.moviekp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    val getMovieList = getMovieListUseCase()

    fun removeMovie(movie: Movie) {
        viewModelScope.launch {
            removeMovieFromDbUseCase(movie)
        }
    }

    fun getDbDescription(movieId: Int): LiveData<Description> {
        return getDescriptionUseCase(movieId)
    }
}
