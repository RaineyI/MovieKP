package com.raineyi.moviekp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DescriptionResponse(

    @SerializedName("description")
    @Expose
    val description: String? = null
)