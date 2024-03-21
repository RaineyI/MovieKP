package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.domain.entities.Movie
import kotlinx.coroutines.launch

//class MovieDetailsViewModel(
//    private val application: Application,
//    private val movie: MovieDto,
//    private val descriptionDto: DescriptionDto
//) : AndroidViewModel(application) {

//    private val movieDao = MovieDatabase.getInstance(application).moviesDao()
//
//    private var _description = MutableLiveData<DescriptionDto>()
//    val description: LiveData<DescriptionDto>
//        get() = _description


//    init {
//        loadDescription()
//    }

//    fun getFavouriteMovie(movieId: Int): LiveData<MovieDbModel> {
//        return movieDao.getFavouriteMovie(movieId)
//    }
//
//    fun getDbDescription(movieId: Int) :LiveData<DescriptionDbModel> {
//        return movieDao.getFavouriteMovieDescription(movieId)
//    }

//    private fun loadDescription() {
//        viewModelScope.launch {
//            try {
//                movie.movieId.let {
//                    val loadingDescription = ApiFactory.apiService.getDescription(it)
//                    _description.value = loadingDescription
//                }
//            } catch (e: Exception) {
//                Log.d("TEST_API", e.message.toString())
//            }
//        }
//    }
//}