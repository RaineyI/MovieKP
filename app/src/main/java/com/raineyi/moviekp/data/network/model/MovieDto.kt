package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDto (

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
    val countries: List<CountryDto>? = null,

    @SerializedName("genres")
    @Expose
    val genres: List<GenreDto>? = null,

    @SerializedName("posterUrl")
    @Expose
    val posterUrl: String? = null
) : Parcelable {

}