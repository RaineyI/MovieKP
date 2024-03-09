package com.raineyi.moviekp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raineyi.moviekp.data.database.dbmodel.DescriptionDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel

@Dao
interface MoviesDao {
    @Query("SELECT * FROM favourite_movies")
    fun getFavouriteMoviesList() : LiveData<List<MovieDbModel>>

    @Query("SELECT * FROM favourite_movies WHERE movieId == :movieId LIMIT 1")
    fun getFavouriteMovie(movieId: Int) : LiveData<MovieDbModel>

    @Query("SELECT * FROM movie_description WHERE movieId == :movieId LIMIT 1")
    fun getFavouriteMovieDescription(movieId: Int) : LiveData<DescriptionDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToDb(movie: MovieDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDescription(description: DescriptionDbModel)

    @Query("DELETE FROM favourite_movies WHERE movieId == :movieId")
    suspend fun removeMovie(movieId: Int)

    @Query("DELETE FROM movie_description WHERE movieId == :movieId")
    suspend fun removeMovieDescription(movieId: Int)
}