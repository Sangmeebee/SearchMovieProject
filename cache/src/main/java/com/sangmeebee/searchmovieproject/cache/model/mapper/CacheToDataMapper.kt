package com.sangmeebee.searchmovieproject.cache.model.mapper

internal interface CacheToDataMapper<T> {
    fun toData(): T
}

internal fun <T> List<CacheToDataMapper<T>>.toData(): List<T> = map { it.toData() }
