package com.sangmeebee.searchmovieproject.domain.usecase

import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class GetBookmarkedMovieFlowUseCaseTest {
    @Test
    fun `레포지토리에 즐겨찾는 영화 정보 조회를 요청한다`() {
        // given
        val movieRepository: MovieRepository = mockk(relaxed = true)
        val getBookmarkedMovieFlowUseCase = GetBookmarkedMovieFlowUseCase(movieRepository)
        // when
        getBookmarkedMovieFlowUseCase()
        // then
        coVerify { movieRepository.getBookmarkedMovies() }
    }
}
