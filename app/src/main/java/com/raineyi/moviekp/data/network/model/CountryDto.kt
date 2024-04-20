package com.raineyi.moviekp.data.network.model

import com.google.gson.annotations.SerializedName


data class CountryDto(
    @SerializedName("country")
//    @Expose
    var country: String? = null
)