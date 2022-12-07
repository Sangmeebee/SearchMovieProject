package com.sangmeebee.searchmovieproject.ui.screen.searchmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sangmeebee.searchmovieproject.domain.usecase.DeleteBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.GetBookmarkedMovieFlowUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.GetMovieFlowUseCase
import com.sangmeebee.searchmovieproject.domain.usecase.PostBookmarkedMovieUseCase
import com.sangmeebee.searchmovieproject.model.MovieModel
import com.sangmeebee.searchmovieproject.model.mapper.toDomain
import com.sangmeebee.searchmovieproject.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    getMovieFlowUseCase: GetMovieFlowUseCase,
    getBookmarkedMovieFlowUseCase: GetBookmarkedMovieFlowUseCase,
    private val postBookmarkedMovieUseCase: PostBookmarkedMovieUseCase,
    private val deleteBookmarkedMovieUseCase: DeleteBookmarkedMovieUseCase,
) : ViewModel() {

    private val query = MutableSharedFlow<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchMovies: Flow<PagingData<MovieModel>> =
        query.flatMapLatest { query ->
            getMovieFlowUseCase(query).map { pagingData ->
                pagingData.map { movieInfo -> movieInfo.toPresentation() }
            }
        }.cachedIn(viewModelScope)

    private val bookmarkedMovies: Flow<List<MovieModel>> = getBookmarkedMovieFlowUseCase()
        .map { it.toPresentation() }
        .catch { emit(emptyList()) }

    val movies: StateFlow<PagingData<MovieModel>> = searchMovies.combine(bookmarkedMovies) { searchMoviePagingData, bookmarkedMovies ->
        searchMoviePagingData.map { searchMovie: MovieModel ->
            val isBookmarked = bookmarkedMovies.any { bookmarkedMovies ->
                bookmarkedMovies.link == searchMovie.link
            }
            searchMovie.copy(isBookmarked = isBookmarked)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PagingData.empty()
    )

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun fetchQuery(newQuery: String) = viewModelScope.launch {
        query.emit(newQuery)
    }

    fun fetchBookmark(movieModel: MovieModel) = viewModelScope.launch {
        if (!movieModel.isBookmarked) {
            postBookmarkedMovieUseCase(movieModel.toDomain())
        } else {
            deleteBookmarkedMovieUseCase(movieModel.toDomain())
        }
    }

    fun fetchErrorState(error: String?) {
        _errorState.value = error
    }
}
