package com.sangmeebee.searchmovieproject.data.di

import com.sangmeebee.searchmovieproject.data.repository.MovieRepositoryImpl
import com.sangmeebee.searchmovieproject.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}
