package com.raineyi.moviekp.domain.entities


data class Movie(
    val movieId: Int,
    val name: String,
    val year: Int,
    val countries: List<Country>,
    val genres: List<Genre>,
    val posterUrl: String,
    var isFavourite: Boolean = false
)