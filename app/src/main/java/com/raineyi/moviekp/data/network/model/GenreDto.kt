package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenreDto (
    @SerializedName("genre")
    @Expose
    val genre: String? = null
)