package com.raineyi.moviekp.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DescriptionDto(

    @SerializedName("filmId")
    @Expose
    var movieId: Int,

    @SerializedName("description")
    @Expose
    val description: String,
)