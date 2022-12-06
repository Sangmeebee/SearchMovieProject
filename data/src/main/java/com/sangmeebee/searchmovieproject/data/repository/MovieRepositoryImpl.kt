package com.sangmeebee.searchmovieproject.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.sangmeebee.searchmovieproject.data.datasource.local.MovieLocalDatasource
import com.sangmeebee.searchmovieproject.data.datasource.remote.MovieRemoteDatasource
import com.sangmeebee.searchmovieproject.data.model.MovieEntity
import com.sangmeebee.searchmovieproject.data.model.mapper.toData
import com.sangmeebee.searchmovieproject.data.model.mapper.toDomain
import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.model.Movie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDatasource,
    private val movieLocalDataSource: MovieLocalDatasource,
) : MovieRepository {

    override fun getMovies(query: String, pageSize: Int): Flow<PagingData<Movie>> =
        movieRemoteDataSource.getMovies(query, pageSize).map { pagingData: PagingData<MovieEntity> ->
            pagingData.map { movie -> movie.toDomain() }
        }

    override fun getBookmarkedMovies(): Flow<List<BookmarkedMovie>> =
        movieLocalDataSource.getBookmarkedMovies().map { it.toDomain() }

    override suspend fun postBookmarkedMovie(movie: BookmarkedMovie): Result<Unit> =
        movieLocalDataSource.postBookmarkedMovie(movie.toData())

    override suspend fun deleteBookmarkedMovie(movie: BookmarkedMovie): Result<Unit> =
        movieLocalDataSource.deleteBookmarkedMovie(movie.toData())
}
