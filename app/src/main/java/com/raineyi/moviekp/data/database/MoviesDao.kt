package com.raineyi.moviekp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.raineyi.moviekp.data.network.model.MovieDto
import androidx.room.Query
import com.raineyi.moviekp.data.network.model.DescriptionDto

@Dao
interface MoviesDao {
    @Query("SELECT * FROM favourite_movies")
    suspend fun getFavouriteMovies() : LiveData<List<MovieDto>>

    @Query("SELECT * FROM favourite_movies WHERE movieId == :movieId LIMIT 1")
    fun getFavouriteMovie(movieId: Int) : LiveData<MovieDto>

    @Query("SELECT * FROM movie_description WHERE movieId == :movieId LIMIT 1")
    fun getFavouriteMovieDescription(movieId: Int) : LiveData<DescriptionDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDescription(description: DescriptionDto)

    @Query("DELETE FROM favourite_movies WHERE movieId == :movieId")
    suspend fun removeMovie(movieId: Int)

    @Query("DELETE FROM movie_description WHERE movieId == :movieId")
    suspend fun removeMovieDescription(movieId: Int)
}