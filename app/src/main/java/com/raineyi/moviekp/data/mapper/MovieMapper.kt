package com.raineyi.moviekp.data.mapper

import com.raineyi.moviekp.data.database.dbmodel.CountryDbModel
import com.raineyi.moviekp.data.database.dbmodel.GenreDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel
import com.raineyi.moviekp.data.network.model.CountryDto
import com.raineyi.moviekp.data.network.model.GenreDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.domain.entities.Movie
import javax.inject.Inject


class MovieMapper @Inject constructor() {
    fun mapDtoToMovie(dto: MovieDto): Movie {

        return Movie(
            movieId = dto.movieId,
            name = dto.name,
            year = dto.year,
            posterUrl = dto.posterUrl,
            countries = mapListCountryDtoToString(dto.countries),
            genres = mapListGenresDtoToString(dto.genres),
            isFavourite = dto.isFavourite

        )
    }

    private fun mapListCountryDtoToString(countries: List<CountryDto>?) =
        countries?.map { it.country }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""

    private fun mapListGenresDtoToString(genres: List<GenreDto>?) =
        genres?.map { it.genre }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""


    fun mapDbModelToMovie(dbModel: MovieDbModel): Movie {

        return Movie(
            movieId = dbModel.movieId,
            name = dbModel.name,
            year = dbModel.year,
            posterUrl = dbModel.posterUrl,
            countries = mapListCountryDbModelToString(dbModel.countries),
            genres = mapListGenresDbModelToString(dbModel.genres),
            isFavourite = dbModel.isFavourite
        )
    }

    private fun mapListCountryDbModelToString(countries: List<CountryDbModel>?) =
        countries?.map { it.country }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""

    private fun mapListGenresDbModelToString(genres: List<GenreDbModel>?) =
        genres?.map { it.genre }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""


    fun mapNullableDbModelToMovie(dbModel: MovieDbModel?): Movie? {
        dbModel?.let {
            return Movie(
                movieId = dbModel.movieId,
                name = dbModel.name,
                year = dbModel.year,
                posterUrl = dbModel.posterUrl,
                countries = mapNullableListCountryDbModelToString(dbModel.countries),
                genres = mapNullableListGenresDbModelToString(dbModel.genres),
                isFavourite = false
            )
        }
        return null
    }

    private fun mapNullableListCountryDbModelToString(countries: List<CountryDbModel>?) =
        countries?.map { it.country }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""

    private fun mapNullableListGenresDbModelToString(genres: List<GenreDbModel>?) =
        genres?.map { it.genre }?.joinToString(", ")
            ?.replaceFirstChar { it.uppercase() } ?: ""

    fun mapMovieToMovieDbModel(movie: Movie): MovieDbModel {
        return MovieDbModel(
            movieId = movie.movieId,
            name = movie.name,
            year = movie.year,
            posterUrl = movie.posterUrl,
            countries = mapListCountryToList(movie.countries),
            genres = mapListGenresToList(movie.genres),
            isFavourite = movie.isFavourite
        )
    }

    private fun mapListCountryToList(countries: String?) =
        countries?.split(", ")?.map { CountryDbModel(it) }

    private fun mapListGenresToList(genres: String?) =
        genres?.split(", ")?.map { GenreDbModel(it) }
}