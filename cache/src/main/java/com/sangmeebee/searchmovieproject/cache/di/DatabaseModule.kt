package com.sangmeebee.searchmovieproject.cache.di

import android.content.Context
import androidx.room.Room
import com.sangmeebee.searchmovieproject.cache.db.AppDatabase
import com.sangmeebee.searchmovieproject.cache.db.BookmarkedMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "SearchMovieProject.db")
        .build()

    @Singleton
    @Provides
    fun provideBookmarkedMovieDao(database: AppDatabase): BookmarkedMovieDao = database.movieBookmarkDao()
}
