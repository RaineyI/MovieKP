package com.raineyi.moviekp.domain

class GetMovieUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovie()
}