package com.sangmeebee.searchmovieproject.domain.usecase

import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PostBookmarkedMovieUseCaseTest {
    @Test
    fun `레포지토리에 즐겨찾는 영화 정보 저장을 요청한다`() = runTest {
        // given
        val movie = BookmarkedMovie(
            title = "<b>광해</b>, 왕이 된 남자",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=83893",
            imageUrl = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0838/83893_P09_112146.jpg",
            director = "추창민",
            actor = "이병헌,류승룡,한효주",
            userRating = "9.25"
        )
        val movieRepository: MovieRepository = mockk(relaxed = true)
        val postBookmarkedMovieUseCase = PostBookmarkedMovieUseCase(movieRepository)
        // when
        postBookmarkedMovieUseCase(movie)
        // then
        coVerify { movieRepository.postBookmarkedMovie(movie) }
    }
}
