package com.artworkspace.themovie.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double
)
