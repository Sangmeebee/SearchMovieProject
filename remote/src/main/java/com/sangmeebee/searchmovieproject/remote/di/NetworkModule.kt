package com.sangmeebee.searchmovieproject.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val SEARCH_MOVIE_BASE_URL = "https://openapi.naver.com/"

    @Singleton
    @Provides
    fun provideConverter(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        with(chain) {
            proceed(
                request().newBuilder()
                    .addHeader("X-Naver-Client-Id", "gtKJPFNgyeGrlp2Ftz2F")
                    .addHeader("X-Naver-Client-Secret", "2oAqr314Pl")
                    .build()
            )
        }
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        converter: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(SEARCH_MOVIE_BASE_URL)
        .addConverterFactory(converter)
        .build()
}
