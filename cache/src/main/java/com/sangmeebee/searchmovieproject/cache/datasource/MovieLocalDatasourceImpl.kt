package com.sangmeebee.searchmovieproject.cache.datasource

import com.sangmeebee.searchmovieproject.cache.db.BookmarkedMovieDao
import com.sangmeebee.searchmovieproject.cache.di.IoDispatcher
import com.sangmeebee.searchmovieproject.cache.model.mapper.toData
import com.sangmeebee.searchmovieproject.cache.model.mapper.toPref
import com.sangmeebee.searchmovieproject.data.datasource.local.MovieLocalDatasource
import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieLocalDatasourceImpl @Inject constructor(
    private val movieDao: BookmarkedMovieDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieLocalDatasource {

    override fun getBookmarkedMovies(): Flow<List<BookmarkedMovieEntity>> =
        movieDao.getBookmarkedMovies().map { it.toData() }

    override suspend fun postBookmarkedMovie(movie: BookmarkedMovieEntity): Result<Unit> = runCatching {
        withContext(ioDispatcher) {
            movieDao.postBookmarkedMovie(movie.toPref())
        }
    }

    override suspend fun deleteBookmarkedMovie(movie: BookmarkedMovieEntity): Result<Unit> = runCatching {
        withContext(ioDispatcher) {
            movieDao.deleteBookmarkedMovie(movie.toPref())
        }
    }
}
