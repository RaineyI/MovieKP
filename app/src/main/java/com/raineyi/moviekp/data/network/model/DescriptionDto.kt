package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DescriptionDto(

    @SerializedName("description")
    @Expose
    val description: String? = null
)