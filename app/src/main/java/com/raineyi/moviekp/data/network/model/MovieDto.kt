package com.raineyi.moviekp.data.network.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//@Entity(tableName = "favourite_movies")
data class MovieDto(

    @PrimaryKey
    @SerializedName("filmId")
//    @Expose
    val movieId: Int,

    @SerializedName("nameRu")
//    @Expose
    val name: String? = null,

    @SerializedName("year")
//    @Expose
    val year: Int? = null,

    @SerializedName("countries")
//    @Expose
    @Embedded
    val countries: List<CountryDto>? = null,

    @SerializedName("genres")
//    @Expose
    @Embedded
    val genres: List<GenreDto>? = null,

    @SerializedName("posterUrl")
//    @Expose
    val posterUrl: String? = null,

    var isFavourite: Boolean = false
)