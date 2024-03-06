package com.raineyi.moviekp.data.database.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class MovieDbModel(

    @PrimaryKey
    val movieId: Int,
    val name: String,
    val year: Int,
    val countries: List<CountryDbModel>,
    val genres: List<GenreDbModel>,
    val posterUrl: String
)