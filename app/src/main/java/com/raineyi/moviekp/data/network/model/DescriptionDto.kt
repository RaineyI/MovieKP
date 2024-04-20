package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.SerializedName


data class DescriptionDto(

    @SerializedName("filmId")
//    @Expose
    var movieId: Int,

    @SerializedName("description")
//    @Expose
    val description: String? = null,
)