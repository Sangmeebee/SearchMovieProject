package com.sangmeebee.searchmovieproject.data.model

data class MovieEntity(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val releaseDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
)
