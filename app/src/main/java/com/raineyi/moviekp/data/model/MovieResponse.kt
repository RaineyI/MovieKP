package com.raineyi.moviekp.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse (

    @SerializedName("films")
    val movies: List<Movie>? = null
)