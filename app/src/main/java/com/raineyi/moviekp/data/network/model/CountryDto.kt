package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("country")
    @Expose
    val country: String? = null
)