package com.raineyi.moviekp.data.database

import androidx.room.TypeConverter
import com.raineyi.moviekp.data.database.dbmodel.CountryDbModel
import com.raineyi.moviekp.data.database.dbmodel.GenreDbModel


class DataConverter {

    @TypeConverter
    fun fromCountriesList(countries: List<CountryDbModel>?): String? {
        return countries?.map { it.country }?.joinToString(", ")

//        return countries?.joinToString(", ") { it.country } ?: ""
    }

    @TypeConverter
    fun toCountryList(countries: String?): List<CountryDbModel>? {
        return countries?.split(", ")?.map { CountryDbModel(it) }
    }

    @TypeConverter
    fun fromGenreList(genres: List<GenreDbModel>?): String? {
        return genres?.map { it.genre }?.joinToString(", ")
//        return genres?.joinToString(", ") { it.genre }
    }

    @TypeConverter
    fun toGenreList(genres: String?): List<GenreDbModel>? {
        return genres?.split(", ")?.map { GenreDbModel(it) }
    }
}