package com.raineyi.moviekp.domain

import javax.inject.Inject

class GetMovieListUseCase @Inject constructor (
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovieList()
}