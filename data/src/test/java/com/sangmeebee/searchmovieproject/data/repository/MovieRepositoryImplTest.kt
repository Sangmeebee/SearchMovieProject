package com.sangmeebee.searchmovieproject.data.repository

import com.sangmeebee.searchmovieproject.data.datasource.remote.MovieRemoteDatasource
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MovieRepositoryImplTest {

    @Test
    fun `데이터소스에 영화 정보 조회를 요청한다`() {
        // given
        val movieRemoteDatasource: MovieRemoteDatasource = mockk(relaxed = true)
        val movieRepositoryImpl: MovieRepository = MovieRepositoryImpl(movieRemoteDatasource)
        // when
        movieRepositoryImpl.getMovies(query = "광해", pageSize = 20)
        // then
        verify { movieRemoteDatasource.getMovies(query = "광해", pageSize = 20) }
    }
}
