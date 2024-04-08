package com.raineyi.moviekp.domain

import javax.inject.Inject

class GetDescriptionUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int) = repository.getDescription(movieId)
}