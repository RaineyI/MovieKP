package com.raineyi.moviekp.data.network

import com.raineyi.moviekp.data.network.model.DescriptionDto
import com.raineyi.moviekp.data.network.model.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("films/top")
    @Headers(
        "X-API-KEY: $API_KEY",
        "Content-Type: application/json"
    )
    suspend fun getMovieResponse(
        @Query(QUERY_PARAM_TYPE) type: String = "TOP_100_POPULAR_FILMS",
        @Query(QUERY_PARAM_PAGE) page: Int = 1
    ): MovieResponseDto

    @GET("films/{movieId}")
    @Headers(
        "X-API-KEY: $API_KEY",
        "Content-Type: application/json"
    )
    suspend fun getDescription(
        @Path(QUERY_PARAM_MOVIE_ID) movieId: Int
    ): DescriptionDto

    companion object {
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
        private const val QUERY_PARAM_TYPE = "type"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_MOVIE_ID = "movieId"
    }
}