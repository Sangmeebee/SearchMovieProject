package com.sangmeebee.searchmovieproject.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangmeebee.searchmovieproject.cache.model.BookmarkedMoviePref

@Database(
    entities = [BookmarkedMoviePref::class],
    version = 1
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun movieBookmarkDao(): BookmarkedMovieDao
}
