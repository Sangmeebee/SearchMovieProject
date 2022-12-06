package com.sangmeebee.searchmovieproject.cache.model.mapper

import com.sangmeebee.searchmovieproject.cache.model.BookmarkedMoviePref
import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity

internal fun BookmarkedMovieEntity.toPref() = BookmarkedMoviePref(
    title = title,
    link = link,
    imageUrl = imageUrl,
    director = director,
    actor = actor,
    userRating = userRating
)
