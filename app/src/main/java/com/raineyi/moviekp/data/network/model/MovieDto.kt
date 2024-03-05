package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favourite_movies")
data class MovieDto (

    @PrimaryKey
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