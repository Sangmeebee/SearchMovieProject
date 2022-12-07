package com.sangmeebee.searchmovieproject.model

data class SearchMovieUiState(
    val isLoading: Boolean = false,
    val isComplete: Boolean = false,
    val errorMessage: String? = null,
)
