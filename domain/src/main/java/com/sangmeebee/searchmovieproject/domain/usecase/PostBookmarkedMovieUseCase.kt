package com.sangmeebee.searchmovieproject.domain.usecase

import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import javax.inject.Inject

class PostBookmarkedMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(bookmarkedMovie: BookmarkedMovie): Result<Unit> =
        movieRepository.postBookmarkedMovie(bookmarkedMovie)
}
