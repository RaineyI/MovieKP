package com.raineyi.moviekp.data.mapper

import com.raineyi.moviekp.data.database.dbmodel.CountryDbModel
import com.raineyi.moviekp.data.database.dbmodel.GenreDbModel
import com.raineyi.moviekp.data.database.dbmodel.MovieDbModel
import com.raineyi.moviekp.data.network.model.CountryDto
import com.raineyi.moviekp.data.network.model.GenreDto
import com.raineyi.moviekp.data.network.model.MovieDto
import com.raineyi.moviekp.domain.entities.Country
import com.raineyi.moviekp.domain.entities.Genre
import com.raineyi.moviekp.domain.entities.Movie
import javax.inject.Inject


class MovieMapper @Inject constructor() {
    fun mapDtoToDbModel(dto: MovieDto): MovieDbModel {

        return MovieDbModel(
            movieId = dto.movieId,
            name = dto.name,
            year = dto.year,
            posterUrl = dto.posterUrl,
            countries = mapCountryListDtoToCountryListDb(dto.countries),
            genres = mapGenreDtoListToGenreDbModelList(dto.genres),
            isFavourite = dto.isFavourite

        )
    }

    private fun mapCountryDtoToDbModel(country: CountryDto?) = CountryDbModel(
            country = country?.country
        )

    private fun mapCountryListDtoToCountryListDb(countryList: List<CountryDto>?) =
        countryList?.map {
            mapCountryDtoToDbModel(it)
        }


    private fun mapGenreDtoToGenreDbModel(genre: GenreDto?) = GenreDbModel(
            genre = genre?.genre
        )

    private fun mapGenreDtoListToGenreDbModelList(genreList: List<GenreDto>?) = genreList?.map {
        mapGenreDtoToGenreDbModel(it)
    }


    fun mapDtoToMovie(dto: MovieDto): Movie {

        return Movie(
            movieId = dto.movieId,
            name = dto.name,
            year = dto.year,
            posterUrl = dto.posterUrl,
            countries = mapCountryListDtoToCountryList(dto.countries),
            genres = mapGenreDtoListToGenreList(dto.genres),
            isFavourite = dto.isFavourite

        )
    }

    private fun mapCountryDtoToCountry(country: CountryDto?) = Country(
            country = country?.country
        )

    private fun mapCountryListDtoToCountryList(countryList: List<CountryDto>?) =
        countryList?.map {
            mapCountryDtoToCountry(it)
        }


    private fun mapGenreDtoToGenre(genre: GenreDto?) = Genre(
            genre = genre?.genre
        )

    private fun mapGenreDtoListToGenreList(genreList: List<GenreDto>?) = genreList?.map {
        mapGenreDtoToGenre(it)
    }


    fun mapDbModelToMovie(dbModel: MovieDbModel): Movie {

        return Movie(
            movieId = dbModel.movieId,
            name = dbModel.name,
            year = dbModel.year,
            posterUrl = dbModel.posterUrl,
            countries = mapCountryListDbModelToCountryList(dbModel.countries),
            genres = mapGenreDbModelListToGenreList(dbModel.genres),
            isFavourite = dbModel.isFavourite
        )
    }

    private fun mapCountryDbModelToCountry(country: CountryDbModel?) = Country(
            country = country?.country
        )

    private fun mapCountryListDbModelToCountryList(countryList: List<CountryDbModel>?) =
        countryList?.map {
            mapCountryDbModelToCountry(it)
        }


    private fun mapGenreDbModelToGenre(genre: GenreDbModel?) = Genre(
            genre = genre?.genre
        )

    private fun mapGenreDbModelListToGenreList(genreList: List<GenreDbModel>?) =
        genreList?.map {
        mapGenreDbModelToGenre(it)
    }

    fun mapDbModelToMovieDto(dbModel: MovieDbModel): MovieDto {
        return MovieDto(
            movieId = dbModel.movieId,
            name = dbModel.name,
            year = dbModel.year,
            posterUrl = dbModel.posterUrl,
            countries = mapCountryListDbModelToCountryListDto(dbModel.countries),
            genres = mapGenreDbModelListToGenreListDto(dbModel.genres),
            isFavourite = dbModel.isFavourite
        )
    }

    private fun mapCountryDbModelToCountryDto(country: CountryDbModel?) = CountryDto(
            country = country?.country
        )

    private fun mapCountryListDbModelToCountryListDto(countryList: List<CountryDbModel>?) =
        countryList?.map {
            mapCountryDbModelToCountryDto(it)
        }


    private fun mapGenreDbModelToGenreDto(genre: GenreDbModel?) = GenreDto(
            genre = genre?.genre
        )

    private fun mapGenreDbModelListToGenreListDto(genreList: List<GenreDbModel>?) = genreList?.map {
        mapGenreDbModelToGenreDto(it)
    }


    fun mapMovieToMovieDbModel(movie: Movie): MovieDbModel {
        return MovieDbModel(
            movieId = movie.movieId,
            name = movie.name,
            year = movie.year,
            posterUrl = movie.posterUrl,
            countries = mapCountryListDbModelToCountryListDbModel(movie.countries),
            genres = mapGenreListToGenreListDbModel(movie.genres),
            isFavourite = movie.isFavourite
        )
    }

    private fun mapCountryToCountryDbModel(country: Country?) = CountryDbModel(
        country = country?.country
    )

    private fun mapCountryListDbModelToCountryListDbModel(countryList: List<Country>?) =
        countryList?.map {
            mapCountryToCountryDbModel(it)
        }


    private fun mapGenreToGenreDbModel(genre: Genre?) = GenreDbModel(
        genre = genre?.genre
    )

    private fun mapGenreListToGenreListDbModel(genreList: List<Genre>?) = genreList?.map {
        mapGenreToGenreDbModel(it)
    }
}