package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.SerializedName


data class GenreDto(
    @SerializedName("genre")
//    @Expose
    var genre: String? = null
)