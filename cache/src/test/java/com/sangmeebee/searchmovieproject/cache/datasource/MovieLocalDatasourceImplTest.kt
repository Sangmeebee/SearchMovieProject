package com.sangmeebee.searchmovieproject.cache.datasource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sangmeebee.searchmovieproject.cache.db.AppDatabase
import com.sangmeebee.searchmovieproject.cache.db.BookmarkedMovieDao
import com.sangmeebee.searchmovieproject.cache.model.mapper.toPref
import com.sangmeebee.searchmovieproject.data.datasource.local.MovieLocalDatasource
import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieLocalDatasourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var movieLocalDatasource: MovieLocalDatasource

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        movieLocalDatasource = MovieLocalDatasourceImpl(database.movieBookmarkDao(), mainDispatcherRule.testDispatcher)
    }

    @After
    fun closeDb() = database.close()
    @Test
    fun `저장한 데이터를 불러온다`() = runTest {
        // given
        val movie = fakeMovie
        // when
        movieLocalDatasource.postBookmarkedMovie(movie)
        // then
        val actual = movieLocalDatasource.getBookmarkedMovies()
        assertThat(actual.first()).isEqualTo(listOf(movie))
    }

    @Test
    fun `영화 정보를 저장한다`() = runTest {
        // given
        val movie = fakeMovie
        // when
        val actual = movieLocalDatasource.postBookmarkedMovie(movie)
        // then
        val currentBookmarkedMovie = movieLocalDatasource.getBookmarkedMovies()
        assertThat(actual.isSuccess).isTrue()
        assertThat(currentBookmarkedMovie.first()).isEqualTo(listOf(movie))
    }

    @Test
    fun `저장에 실패하면 예외를 반환한다`() = runTest {
        // given
        val bookmarkedMovieDao: BookmarkedMovieDao = mockk()
        movieLocalDatasource = MovieLocalDatasourceImpl(bookmarkedMovieDao, mainDispatcherRule.testDispatcher)
        val movie = fakeMovie
        coEvery { bookmarkedMovieDao.postBookmarkedMovie(movie.toPref()) } throws IllegalStateException()
        // when
        val actual = movieLocalDatasource.postBookmarkedMovie(movie)
        // then
        assertThat(actual.isFailure).isTrue()
    }

    @Test
    fun `영화 정보를 삭제한다`() = runTest {
        // given
        val movie = fakeMovie
        // when
        movieLocalDatasource.postBookmarkedMovie(movie)
        val actual = movieLocalDatasource.deleteBookmarkedMovie(movie)
        // then
        val currentMovie = movieLocalDatasource.getBookmarkedMovies()
        assertThat(actual.isSuccess).isTrue()
        assertThat(currentMovie.first()).isEmpty()
    }

    @Test
    fun `삭제에 실패하면 해당 예외를 반환한다`() = runTest {
        // given
        val bookmarkedMovieDao: BookmarkedMovieDao = mockk()
        movieLocalDatasource = MovieLocalDatasourceImpl(bookmarkedMovieDao, mainDispatcherRule.testDispatcher)
        val movie = fakeMovie
        coEvery { bookmarkedMovieDao.deleteBookmarkedMovie(movie.toPref()) } throws IllegalStateException()
        // when
        val actual = movieLocalDatasource.deleteBookmarkedMovie(movie)
        // then
        assertThat(actual.isFailure).isTrue()
    }

    companion object {
        private val fakeMovie = BookmarkedMovieEntity(
            title = "<b>광해</b>, 왕이 된 남자",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=83893",
            imageUrl = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0838/83893_P09_112146.jpg",
            director = "추창민",
            actor = "이병헌,류승룡,한효주",
            userRating = "9.25"
        )
    }
}
