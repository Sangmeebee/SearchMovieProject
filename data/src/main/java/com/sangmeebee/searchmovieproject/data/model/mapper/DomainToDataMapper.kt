package com.sangmeebee.searchmovieproject.data.model.mapper

import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity
import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie

internal fun BookmarkedMovie.toData(): BookmarkedMovieEntity = BookmarkedMovieEntity(
    title = title,
    link = link,
    imageUrl = imageUrl,
    director = director,
    actor = actor,
    userRating = userRating
)
