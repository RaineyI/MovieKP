package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import androidx.room.Embedded
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
    val movieId: Int,

    @SerializedName("nameRu")
    @Expose
    val name: String,

    @SerializedName("year")
    @Expose
    val year: Int,

    @SerializedName("countries")
    @Expose
    @Embedded
    val countries: List<CountryDto>,

    @SerializedName("genres")
    @Expose
    @Embedded
    val genres: List<GenreDto>,

    @SerializedName("posterUrl")
    @Expose
    val posterUrl: String
) : Parcelable