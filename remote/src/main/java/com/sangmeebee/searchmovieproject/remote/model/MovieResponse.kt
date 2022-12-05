package com.sangmeebee.searchmovieproject.remote.model

import com.google.gson.annotations.SerializedName

internal data class MovieResponse(
    @SerializedName("lastBuildDate")
    val offsetTime: String,
    @SerializedName("total")
    val totalCount: Long,
    @SerializedName("start")
    val pageStart: Int,
    @SerializedName("display")
    val pageSize: Int,
    @SerializedName("items")
    val items: List<MovieInfoResponse>,
)
