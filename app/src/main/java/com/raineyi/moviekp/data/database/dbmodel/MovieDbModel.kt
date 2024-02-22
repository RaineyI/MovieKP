package com.raineyi.moviekp.data.database.dbmodel

import androidx.room.PrimaryKey


data class MovieDbModel (

    @PrimaryKey
    val movieId: Int? = null,
    val name: String? = null,
    val year: Int? = null,
    val countries: List<CountryDbModel>? = null,
    val genres: List<GenreDbModel>? = null,
    val posterUrl: String? = null
)