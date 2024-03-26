package com.raineyi.moviekp.domain.entities


data class Movie(
    val movieId: Int?,
    val name: String? = null,
    val year: Int? = null,
    val countries: List<Country>? = null,
    val genres: List<Genre>? = null,
    val posterUrl: String? = null,
    var isFavourite: Boolean = false
)