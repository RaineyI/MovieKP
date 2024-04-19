package com.raineyi.moviekp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val movieId: Int?,
    val name: String? = null,
    val year: Int? = null,
    val countries: String,
    val genres: String,
    val posterUrl: String? = null,
    var isFavourite: Boolean = false
): Parcelable