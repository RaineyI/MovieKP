package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class DescriptionDto(

    @SerializedName("filmId")
    @Expose
    var movieId: Int?,

    @SerializedName("description")
    @Expose
    val description: String? = null,
)