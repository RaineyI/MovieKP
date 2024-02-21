package com.raineyi.moviekp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie (

    @SerializedName("filmId")
    @Expose
    val movieId: Int? = null,

    @SerializedName("nameRu")
    @Expose
    val name: String? = null,

    @SerializedName("year")
    @Expose
    val year: Int? = null,

    @SerializedName("countries")
    @Expose
    val countries: List<Country>? = null,

    @SerializedName("genres")
    @Expose
    val genres: List<Genre>? = null,

    @SerializedName("posterUrl")
    @Expose
    val posterUrl: String? = null
)