package com.raineyi.moviekp.data.database.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_description")
data class DescriptionDbModel(

    @PrimaryKey
    var movieId: Int?,

    val description: String? = null
)