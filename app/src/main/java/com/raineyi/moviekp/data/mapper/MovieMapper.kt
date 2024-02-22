package com.raineyi.moviekp.data.mapper

import com.raineyi.moviekp.data.database.dbmodel.CountryDbModel
import com.raineyi.moviekp.data.database.dbmodel.GenreDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel
import com.raineyi.moviekp.data.network.model.CountryDto
import com.raineyi.moviekp.data.network.model.GenreDto
import com.raineyi.moviekp.data.network.model.MovieDto


class MovieMapper {
    fun mapDtoToDbModel(dto: MovieDto): MovieDbModel {

        return MovieDbModel(
            movieId = dto.movieId,
            name = dto.name,
            year = dto.year,
            countries = mapCountryListDtoToCountryListDb(dto.countries),
            genres = mapGenreDtoListToGenreDbModelList(dto.genres),
            posterUrl = dto.posterUrl
        )
    }

    private fun mapCountryDtoToDbModel(country: CountryDto): CountryDbModel {
        return CountryDbModel(
            country = country.country
        )
    }

    private fun mapCountryListDtoToCountryListDb(countryList: List<CountryDto>?) =
        countryList?.map {
            mapCountryDtoToDbModel(it)
        }


    private fun mapGenreDtoToGenreDbModel(genre: GenreDto) : GenreDbModel {
        return GenreDbModel (
            genre = genre.genre
        )
    }

    private fun mapGenreDtoListToGenreDbModelList(genreList: List<GenreDto>?) = genreList?.map {
        mapGenreDtoToGenreDbModel(it)
    }
}