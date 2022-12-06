package com.sangmeebee.searchmovieproject.domain.model

data class BookmarkedMovie(
    val title: String,
    val link: String,
    val imageUrl: String?,
    val director: String,
    val actor: String,
    val userRating: String,
)
