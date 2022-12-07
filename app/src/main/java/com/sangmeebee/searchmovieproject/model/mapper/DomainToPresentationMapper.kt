package com.sangmeebee.searchmovieproject.model.mapper

import com.sangmeebee.searchmovieproject.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovieproject.domain.model.Movie
import com.sangmeebee.searchmovieproject.model.MovieModel

internal fun Movie.toPresentation() = MovieModel(
    title = title,
    link = link,
    imageUrl = imageUrl,
    director = director.removeSuffix("|"),
    actor = actor.removeSuffix("|").replace("|", ","),
    userRating = userRating,
    isBookmarked = false
)

internal fun BookmarkedMovie.toPresentation() = MovieModel(
    title = title,
    link = link,
    imageUrl = imageUrl,
    director = director,
    actor = actor,
    userRating = userRating,
    isBookmarked = true
)

internal fun List<BookmarkedMovie>.toPresentation() = map { it.toPresentation() }
