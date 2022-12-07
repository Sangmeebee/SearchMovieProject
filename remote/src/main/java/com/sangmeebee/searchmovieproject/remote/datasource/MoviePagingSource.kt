package com.sangmeebee.searchmovieproject.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sangmeebee.searchmovieproject.data.model.MovieEntity
import com.sangmeebee.searchmovieproject.remote.model.mapper.toData
import com.sangmeebee.searchmovieproject.remote.service.MovieApi
import com.sangmeebee.searchmovieproject.remote.util.EmptyQueryException
import com.sangmeebee.searchmovieproject.remote.util.HttpConnectionException
import com.sangmeebee.searchmovieproject.remote.util.IllegalSearchMovieException
import retrofit2.HttpException
import java.io.IOException

internal class MoviePagingSource(
    private val movieApi: MovieApi,
    private val query: String,
    private val pageSize: Int,
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        try {
            if (query.isEmpty()) {
                throw EmptyQueryException()
            }
            val start = params.key ?: STARTING_PAGE_INDEX
            val response = movieApi.getMovies(
                query = query,
                display = pageSize,
                start = start
            )
            return LoadResult.Page(
                data = response.items.toData(),
                prevKey = if (start == STARTING_PAGE_INDEX) null else start - pageSize,
                nextKey = if (response.pageSize < pageSize || start + pageSize > LAST_PAGE_INDEX) null else start + pageSize
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(HttpConnectionException())
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(IllegalSearchMovieException(e.message))
        } catch (e: EmptyQueryException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val LAST_PAGE_INDEX = 1000
    }
}
