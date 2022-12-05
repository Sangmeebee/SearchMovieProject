package com.sangmeebee.searchmovieproject.data.model.mapper

internal interface DataToDomainMapper<T> {
    fun toDomain(): T
}

internal fun <T> List<DataToDomainMapper<T>>.toDomain(): List<T> = map { it.toDomain() }
