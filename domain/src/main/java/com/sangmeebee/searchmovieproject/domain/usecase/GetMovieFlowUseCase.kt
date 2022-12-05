package com.sangmeebee.searchmovieproject.domain.usecase

import androidx.paging.PagingData
import com.sangmeebee.searchmovieproject.domain.model.Movie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieFlowUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(query: String, pageSize: Int = 20): Flow<PagingData<Movie>> =
        movieRepository.getMovies(query, pageSize)
}
