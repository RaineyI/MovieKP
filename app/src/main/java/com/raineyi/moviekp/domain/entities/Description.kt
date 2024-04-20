package com.raineyi.moviekp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Description(
    val movieId: Int,
    val description: String
) : Parcelable