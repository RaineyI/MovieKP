package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CountryDto(
    @SerializedName("country")
    @Expose
    var country: String? = null
)