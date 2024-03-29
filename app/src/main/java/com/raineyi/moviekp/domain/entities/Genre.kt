package com.raineyi.moviekp.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre (
    val genre: String? = null
): Parcelable