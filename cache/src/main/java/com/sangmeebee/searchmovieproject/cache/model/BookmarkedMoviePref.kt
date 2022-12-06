package com.sangmeebee.searchmovieproject.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sangmeebee.searchmovieproject.cache.model.mapper.CacheToDataMapper
import com.sangmeebee.searchmovieproject.data.model.BookmarkedMovieEntity

@Entity(tableName = "bookmarked_movie")
internal data class BookmarkedMoviePref(
    val title: String,
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val link: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    val director: String,
    val actor: String,
    @ColumnInfo(name = "user_rating")
    val userRating: String,
) : CacheToDataMapper<BookmarkedMovieEntity> {
    override fun toData(): BookmarkedMovieEntity = BookmarkedMovieEntity(
        title = title,
        link = link,
        imageUrl = imageUrl,
        director = director,
        actor = actor,
        userRating = userRating
    )
}
