package com.raineyi.moviekp.domain

class LoadMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = repository.loadMovies(page)
}