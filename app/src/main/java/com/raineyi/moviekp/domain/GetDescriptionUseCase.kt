package com.raineyi.moviekp.domain

class GetDescriptionUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int) = repository.getDescription(movieId)
}