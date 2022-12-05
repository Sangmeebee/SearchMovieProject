package com.sangmeebee.searchmovieproject.data.datasource.remote

import androidx.paging.PagingData
import com.sangmeebee.searchmovieproject.data.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDatasource {
    fun getMovies(query: String, pageSize: Int): Flow<PagingData<MovieEntity>>
}
