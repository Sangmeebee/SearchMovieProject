package com.sangmeebee.searchmovieproject.remote.service

import com.sangmeebee.searchmovieproject.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieApi {

    @GET("v1/search/movie.json")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
    ): MovieResponse
}
