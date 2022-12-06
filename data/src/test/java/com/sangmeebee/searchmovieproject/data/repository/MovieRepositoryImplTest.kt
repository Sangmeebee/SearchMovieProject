package com.sangmeebee.searchmovieproject.data.repository

import com.sangmeebee.searchmovieproject.data.datasource.local.MovieLocalDatasource
import com.sangmeebee.searchmovieproject.data.datasource.remote.MovieRemoteDatasource
import com.sangmeebee.searchmovieproject.data.model.mapper.toData
import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    private lateinit var movieRemoteDatasource: MovieRemoteDatasource
    private lateinit var movieLocalDatasource: MovieLocalDatasource
    private lateinit var movieRepositoryImpl: MovieRepository

    @Before
    fun setup() {
        movieRemoteDatasource = mockk(relaxed = true)
        movieLocalDatasource = mockk(relaxed = true)
        movieRepositoryImpl = MovieRepositoryImpl(movieRemoteDatasource, movieLocalDatasource)
    }

    @Test
    fun `데이터소스에 영화 정보 조회를 요청한다`() {
        // when
        movieRepositoryImpl.getMovies(query = "광해", pageSize = 20)
        // then
        verify { movieRemoteDatasource.getMovies(query = "광해", pageSize = 20) }
    }

    @Test
    fun `데이터소스에 즐겨찾는 영화 조회 요청한다`() {
        // when
        movieRepositoryImpl.getBookmarkedMovies()
        // then
        verify { movieLocalDatasource.getBookmarkedMovies() }
    }

    @Test
    fun `데이터소스에 즐겨찾는 영화 저장을 요청한다`() = runTest {
        // when
        movieRepositoryImpl.postBookmarkedMovie(fakeMovie)
        // then
        coVerify { movieLocalDatasource.postBookmarkedMovie(fakeMovie.toData()) }
    }

    @Test
    fun `데이터소스에 즐겨찾는 영화 삭제를 요청한다`() = runTest {
        // when
        movieRepositoryImpl.deleteBookmarkedMovie(fakeMovie)
        // then
        coVerify { movieLocalDatasource.deleteBookmarkedMovie(fakeMovie.toData()) }
    }

    companion object {
        private val fakeMovie = BookmarkedMovie(
            title = "<b>광해</b>, 왕이 된 남자",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=83893",
            imageUrl = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0838/83893_P09_112146.jpg",
            director = "추창민",
            actor = "이병헌,류승룡,한효주",
            userRating = "9.25"
        )
    }
}
