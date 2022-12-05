package com.sangmeebee.searchmovieproject.remote.datasource

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.sangmeebee.searchmovieproject.data.datasource.remote.MovieRemoteDatasource
import com.sangmeebee.searchmovieproject.remote.service.MovieApi
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieRemoteDatasourceImplTest {

    @Test
    fun `영화 정보를 요청하면 페이징 데이터 flow를 반환한다`() = runTest {
        // given
        val movieApi: MovieApi = mockk(relaxed = true)
        val movieRemoteDatasourceImpl: MovieRemoteDatasource = MovieRemoteDatasourceImpl(movieApi)
        // when
        val actual = movieRemoteDatasourceImpl.getMovies("광해", 20)
        // then
        assertThat(actual.first()).isInstanceOf(PagingData::class.java)
    }
}
