package com.raineyi.moviekp.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDto (
    @SerializedName("genre")
    @Expose
    var genre: String
): Parcelable