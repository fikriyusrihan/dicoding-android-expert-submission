package com.artworkspace.themovie.utils

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
}