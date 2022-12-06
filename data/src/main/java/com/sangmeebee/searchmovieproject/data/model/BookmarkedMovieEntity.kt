package com.sangmeebee.searchmovieproject.data.model

data class BookmarkedMovieEntity(
    val title: String,
    val link: String,
    val imageUrl: String?,
    val director: String,
    val actor: String,
    val userRating: String,
)
