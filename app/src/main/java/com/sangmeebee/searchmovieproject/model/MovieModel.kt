package com.sangmeebee.searchmovieproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val title: String,
    val link: String,
    val imageUrl: String?,
    val director: String,
    val actor: String,
    val userRating: String,
    val isBookmarked: Boolean,
) : Parcelable
