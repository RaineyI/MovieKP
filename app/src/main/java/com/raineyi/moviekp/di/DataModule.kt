package com.raineyi.moviekp.di

import android.app.Application
import com.raineyi.moviekp.data.database.MovieDao
import com.raineyi.moviekp.data.database.MovieDatabase
import com.raineyi.moviekp.data.repository.MovieRepositoryImpl
import com.raineyi.moviekp.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    companion object {
        @Provides
        fun provideMovieDao(
            application: Application
        ): MovieDao {
            return MovieDatabase.getInstance(application).moviesDao()
        }
    }
}