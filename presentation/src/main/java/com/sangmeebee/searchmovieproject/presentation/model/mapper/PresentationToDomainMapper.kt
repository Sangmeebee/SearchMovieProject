package com.sangmeebee.searchmovieproject.presentation.model.mapper

import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.presentation.model.MovieModel

internal fun MovieModel.toDomain() = BookmarkedMovie(
    title = title,
    link = link,
    imageUrl = imageUrl,
    director = director,
    actor = actor,
    userRating = userRating,
)
