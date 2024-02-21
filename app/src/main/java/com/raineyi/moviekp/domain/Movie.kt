package com.raineyi.moviekp.domain

import com.raineyi.moviekp.data.model.Country
import com.raineyi.moviekp.data.model.Genre

data class Movie(
    val id: Int? = null,
    val name: String? = null,
    val year: Int? = null,
    val countries: List<Country>? = null,
    val genres: List<Genre>? = null,
    val posterUrl: String? = null
)