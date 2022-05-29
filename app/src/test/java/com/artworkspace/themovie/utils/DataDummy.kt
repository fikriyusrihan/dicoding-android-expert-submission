package com.artworkspace.themovie.utils

import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie

object DataDummy {
    fun generateMovies(): List<Movie> {
        val movies = mutableListOf<Movie>()
        for (i in 0..10) {
            val movie = Movie(
                i,
                title = "The No-$i Movie",
                overview = "Overview of this movie",
                posterPath = "Path to the poster",
                voteAverage = 5.0
            )

            movies.add(movie)
        }

        return movies
    }

    fun generateMovieCast(): List<Cast> {
        val casts = mutableListOf<Cast>()
        for (i in 0..5) {
            val cast = Cast(
                id = i,
                name = "Cast No.$i",
                character = "Character No.$i",
                profilePath = "Profile path No.$i"
            )

            casts.add(cast)
        }

        return casts
    }
}