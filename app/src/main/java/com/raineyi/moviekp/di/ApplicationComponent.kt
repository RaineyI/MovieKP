package com.raineyi.moviekp.di

import android.app.Application
import com.raineyi.moviekp.presentation.FavouriteMoviesFragment
import com.raineyi.moviekp.presentation.MovieDetailsFragment
import com.raineyi.moviekp.presentation.PopularMoviesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: PopularMoviesFragment)

    fun inject(fragment: FavouriteMoviesFragment)

    fun inject(fragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}