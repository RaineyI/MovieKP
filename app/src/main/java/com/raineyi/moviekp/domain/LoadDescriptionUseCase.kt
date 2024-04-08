package com.raineyi.moviekp.domain

import javax.inject.Inject

class LoadDescriptionUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.loadDescription(movieId)
}