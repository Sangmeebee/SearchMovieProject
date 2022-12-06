package com.sangmeebee.searchmovieproject.data.datasource.local

import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDatasource {
    fun getBookmarkedMovies(): Flow<List<BookmarkedMovieEntity>>
    suspend fun postBookmarkedMovie(movie: BookmarkedMovieEntity): Result<Unit>
    suspend fun deleteBookmarkedMovie(movie: BookmarkedMovieEntity): Result<Unit>
}
