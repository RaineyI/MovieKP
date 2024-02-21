package com.raineyi.moviekp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("genre")
    @Expose
    val genre: String? = null
)