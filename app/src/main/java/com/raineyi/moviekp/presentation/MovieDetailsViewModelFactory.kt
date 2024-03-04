package com.raineyi.moviekp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raineyi.moviekp.data.network.model.MovieDto

class MovieDetailsViewModelFactory(
    private val application: Application,
    private val movie: MovieDto
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application, movie) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}