package com.sangmeebee.searchmovieproject.remote.datasource

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.sangmeebee.searchmovieproject.data.model.MovieEntity
import com.sangmeebee.searchmovieproject.domain.util.EmptyQueryException
import com.sangmeebee.searchmovieproject.domain.util.HttpConnectionException
import com.sangmeebee.searchmovieproject.domain.util.IllegalSearchMovieException
import com.sangmeebee.searchmovieproject.remote.model.MovieInfoResponse
import com.sangmeebee.searchmovieproject.remote.model.MovieResponse
import com.sangmeebee.searchmovieproject.remote.model.mapper.toData
import com.sangmeebee.searchmovieproject.remote.service.MovieApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MoviePagingSourceTest {
    private lateinit var moviePagingSource: MoviePagingSource
    private val mockWebServer = MockWebServer()
    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        mockWebServer.start()
        movieApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
        moviePagingSource = MoviePagingSource(movieApi, "광해", 1)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `데이터 요청을 처음 시도하면 성공하면 prevKey가 null이고 nextKey가 key + loadSize인 페이징 데이터를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/movie_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        // then
        val expected = PagingSource.LoadResult.Page(
            data = fakeMovieResponse.items.toData(),
            prevKey = null,
            nextKey = 2
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `데이터 요청을 추가적으로 시도해 성공하면 prevKey가 key - loadSize이고 nextKey가 key + loadSize인 페이징 데이터를 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(200).setBody(File("src/test/resources/movie_200.json").readText())
        mockWebServer.enqueue(response)
        // when
        val actual = moviePagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        // then
        val expected = PagingSource.LoadResult.Page(
            data = fakeMovieResponse.items.toData(),
            prevKey = 1,
            nextKey = 3
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `non-2xx HTTP 상태 코드를 반환하면 하면 IllegalSearchMovieException을 반환한다`() = runTest {
        // given
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)
        // when
        val actual = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        // then
        val expected = PagingSource.LoadResult.Error<Int, MovieEntity>(IllegalSearchMovieException("HTTP 400 Client Error"))
        assertThat(actual.toString()).isEqualTo(expected.toString())
    }

    @Test
    fun `네트워크가 끊겨있으면 HttpConnectionException을 반환한다`() = runTest {
        // given
        val response = MockResponse()
        mockWebServer.enqueue(response)
        // when
        val actual = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        // then
        val expected = PagingSource.LoadResult.Error<Int, MovieEntity>(HttpConnectionException())
        assertThat(actual.toString()).isEqualTo(expected.toString())
    }

    @Test
    fun `검색어가 비어있으면 EmptyQueryException을 반환한다`() = runTest {
        // given
        moviePagingSource = MoviePagingSource(movieApi, "", 1)
        val response = MockResponse().setResponseCode(200)
        mockWebServer.enqueue(response)
        // when
        val actual = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        // then
        val expected = PagingSource.LoadResult.Error<Int, MovieEntity>(EmptyQueryException())
        assertThat(actual.toString()).isEqualTo(expected.toString())
    }

    companion object {
        private val fakeMovieResponse = MovieResponse(
            offsetTime = "Mon, 05 Dec 2022 20:46:10 +0900",
            totalCount = 1,
            pageStart = 1,
            pageSize = 1,
            items = listOf(
                MovieInfoResponse(
                    title = "<b>광해</b>, 왕이 된 남자",
                    subtitle = "Masquerade",
                    link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=83893",
                    imageUrl = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0838/83893_P09_112146.jpg",
                    releaseDate = "2012",
                    director = "추창민|",
                    actor = "이병헌|류승룡|한효주|",
                    userRating = "9.25"
                )
            )
        )
    }
}
