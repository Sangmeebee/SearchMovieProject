package com.sangmeebee.searchmovieproject.domain.usecase

import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedMovieFlowUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<List<BookmarkedMovie>> =
        movieRepository.getBookmarkedMovies()
}
