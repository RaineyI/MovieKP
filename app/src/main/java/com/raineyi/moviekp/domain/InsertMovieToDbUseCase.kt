package com.raineyi.moviekp.domain

import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie

class InsertMovieToDbUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie, description: Description) =
        repository.insertMovieToDb(movie, description)
}