package com.sangmeebee.searchmovieproject.presentation.model

data class SearchMovieUiState(
    val isLoading: Boolean = false,
    val isComplete: Boolean = false,
    val errorMessage: String? = null,
)
