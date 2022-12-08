package com.sangmeebee.searchmovieproject.ui.screen.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovieproject.domain.usecase.DeleteBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.PostBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.model.MovieModel
import com.sangmeebee.searchmovieproject.model.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val postBookmarkedMovieUseCase: PostBookmarkedMovieUseCase,
    private val deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase,
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieModel?>(null)
    val movie = _movie.asStateFlow()

    fun fetchMovie(movie: MovieModel) {
        _movie.value = movie
    }

    fun fetchBookmark(movieModel: MovieModel) = viewModelScope.launch {
        if (!movieModel.isBookmarked) {
            postBookmarkedMovieUseCase(movieModel.toDomain())
            _movie.update { it?.copy(isBookmarked = true) }
        } else {
            deleteBookmarkedMovieUseCase(movieModel.toDomain())
            _movie.update { it?.copy(isBookmarked = false) }
        }
    }
}
