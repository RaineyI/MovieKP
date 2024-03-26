package com.raineyi.moviekp.domain

class LoadDescriptionUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.loadDescription(movieId)
}