package com.raineyi.moviekp.domain

import javax.inject.Inject

class LoadMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = repository.loadMovies(page)
}