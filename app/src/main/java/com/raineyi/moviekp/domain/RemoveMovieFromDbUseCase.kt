package com.raineyi.moviekp.domain

import com.raineyi.moviekp.domain.entities.Description
import com.raineyi.moviekp.domain.entities.Movie
import javax.inject.Inject

class RemoveMovieFromDbUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie, description: Description) =
        repository.removeMovieFromDb(movie, description)
}