package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieResponseDto (

    @SerializedName("films")
    val movies: List<MovieDto>? = null
)