package com.raineyi.moviekp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raineyi.moviekp.data.network.ApiFactory
import com.raineyi.moviekp.data.network.model.MovieDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    var currentPage = 1

    private var _listOfMovies = MutableLiveData<List<MovieDto>>()
    val listOfMovies: LiveData<List<MovieDto>> = _listOfMovies

    init {
        loadMovies(currentPage)
    }

    fun loadMovies(page: Int) {
        val disposable = ApiFactory.apiService.getMovieResponse(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currentPage++
                //TODO() исправить !! Подгрузка новых страниц
                if (it.movies != null) {
                    _listOfMovies.value = it.movies!!
                }
//                val currentMovies = _listOfMovies.value?.toMutableList()
//                if (currentMovies != null) {
//                    it.let { currentMovies.addAll(it) }
//                    _listOfMovies.value = currentMovies
//                } else {
//                    _listOfMovies.value = it
//                }
//                currentPage = page
            }, {
                Log.d("TEST_API", it.message.toString())
            })

        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}


//val disposable = ApiFactory.apiService.getDescriptionResponse(1115471)
//    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
//    .subscribe({
//        Log.d("TEST_API", it.toString())
//    },{
//        Log.d("TEST_API", it.message.toString())
//    })
//compositeDisposable.add(disposable)
