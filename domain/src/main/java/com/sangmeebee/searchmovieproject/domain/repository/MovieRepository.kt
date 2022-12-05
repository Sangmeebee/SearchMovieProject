package com.sangmeebee.searchmovieproject.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovieproject.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String, pageSize: Int): Flow<PagingData<Movie>>
}
