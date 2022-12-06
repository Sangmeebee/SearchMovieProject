package com.sangmeebee.searchmovieproject.cache.di

import com.sangmeebee.searchmovieproject.cache.datasource.MovieLocalDatasourceImpl
import com.sangmeebee.searchmovieproject.data.datasource.local.MovieLocalDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatasourceModule {

    @Singleton
    @Binds
    abstract fun bindMovieLocalDataSource(impl: MovieLocalDatasourceImpl): MovieLocalDatasource
}
