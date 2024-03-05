package com.raineyi.moviekp.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_description")
data class DescriptionDto(

    @PrimaryKey
    @SerializedName("filmId")
    @Expose
    val movieId: Int? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null
)