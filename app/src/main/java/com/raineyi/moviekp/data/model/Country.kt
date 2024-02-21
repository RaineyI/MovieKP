package com.raineyi.moviekp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country")
    @Expose
    val country: String? = null
)