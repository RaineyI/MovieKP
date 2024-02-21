package com.raineyi.moviekp.domain

class GetMovieListUseCase (
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovieList()
}