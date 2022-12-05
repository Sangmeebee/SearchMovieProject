package com.sangmeebee.searchmovieproject.domain.usecase

import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class GetMovieFlowUseCaseTest {
    @Test
    fun `레포지토리에 영화 정보 조회를 요청한다`() {
        // given
        val movieRepository: MovieRepository = mockk(relaxed = true)
        val getMovieUseCase = GetMovieFlowUseCase(movieRepository)
        // when
        getMovieUseCase("광해")
        // then
        coVerify { movieRepository.getMovies("광해", 20) }
    }
}
