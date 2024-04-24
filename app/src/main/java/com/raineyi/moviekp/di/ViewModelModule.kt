package com.raineyi.moviekp.di


import androidx.lifecycle.ViewModel
import com.raineyi.moviekp.presentation.viewmodels.FavouriteMoviesViewModel
import com.raineyi.moviekp.presentation.viewmodels.PopularMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel::class)
    fun bindPopularMoviesViewModel(viewModel: PopularMoviesViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FavouriteMoviesViewModel::class)
    fun bindFavouriteMoviesViewModel(viewModel: FavouriteMoviesViewModel): ViewModel
}