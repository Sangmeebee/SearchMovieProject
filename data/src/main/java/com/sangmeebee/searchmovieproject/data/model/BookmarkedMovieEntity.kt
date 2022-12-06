package com.sangmeebee.searchmovieproject.data.model

import com.sangmeebee.searchmovieproject.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie

data class BookmarkedMovieEntity(
    val title: String,
    val link: String,
    val imageUrl: String?,
    val director: String,
    val actor: String,
    val userRating: String,
) : DataToDomainMapper<BookmarkedMovie> {
    override fun toDomain(): BookmarkedMovie = BookmarkedMovie(
        title = title,
        link = link,
        imageUrl = imageUrl,
        director = director,
        actor = actor,
        userRating = userRating
    )
}
