package com.sangmeebee.searchmovieproject.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sangmeebee.searchmovieproject.data.datasource.remote.MovieRemoteDatasource
import com.sangmeebee.searchmovieproject.data.model.MovieEntity
import com.sangmeebee.searchmovieproject.remote.service.MovieApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MovieRemoteDatasourceImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRemoteDatasource {
    override fun getMovies(query: String, pageSize: Int): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, query, pageSize) }
        ).flow
}
