package com.sangmeebee.searchmovieproject.ui.screen.bookmarkedmovie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovieproject.R
import com.sangmeebee.searchmovieproject.domain.usecase.DeleteBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.GetBookmarkedMovieFlowUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.PostBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.model.MovieModel
import com.sangmeebee.searchmovieproject.model.mapper.toDomain
import com.sangmeebee.searchmovieproject.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedMovieViewModel @Inject constructor(
    @ApplicationContext context: Context,
    getBookmarkedMovieFlowUseCase: GetBookmarkedMovieFlowUseCase,
    private val postBookmarkedMovieUseCase: PostBookmarkedMovieUseCase,
    private val deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase,
) : ViewModel() {

    val bookmarkedMovies: StateFlow<List<MovieModel>> = getBookmarkedMovieFlowUseCase()
        .map { it.toPresentation() }
        .catch {
            _errorState.value = context.getString(R.string.bookmarked_movie_error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun fetchBookmark(movieModel: MovieModel) = viewModelScope.launch {
        if (!movieModel.isBookmarked) {
            postBookmarkedMovieUseCase(movieModel.toDomain())
        } else {
            deleteBookmarkedMovieUseCase(movieModel.toDomain())
        }
    }
}
