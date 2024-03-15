package com.raineyi.moviekp.domain

import com.raineyi.moviekp.domain.entities.Movie

class InsertMovieToDbUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movie: Movie) = repository.insertMovieToDb(movie)
}