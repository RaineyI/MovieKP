package com.raineyi.moviekp.data.database.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class MovieDbModel(

    @PrimaryKey
    val movieId: Int,
    val name: String? = null,
    val year: Int? = null,
    val countries: List<CountryDbModel>? = null,
    val genres: List<GenreDbModel>? = null,
    val posterUrl: String? = null,
    var isFavourite: Boolean = false
)