package com.sangmeebee.searchmovieproject.cache.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sangmeebee.searchmovieproject.cache.model.BookmarkedMoviePref
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BookmarkedMovieDao {

    @Query("SELECT * FROM bookmarked_movie")
    fun getBookmarkedMovies(): Flow<List<BookmarkedMoviePref>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postBookmarkedMovie(movie: BookmarkedMoviePref)

    @Delete
    suspend fun deleteBookmarkedMovie(movie: BookmarkedMoviePref)
}
