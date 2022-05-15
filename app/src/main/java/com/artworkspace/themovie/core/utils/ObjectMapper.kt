package com.artworkspace.themovie.core.utils

import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
import com.artworkspace.themovie.core.data.source.remote.response.CastResponse
import com.artworkspace.themovie.core.data.source.remote.response.MovieResponse
import com.artworkspace.themovie.core.domain.model.Cast
import com.artworkspace.themovie.core.domain.model.Movie

/**
 * Map MovieEntity object to Movie object
 *
 * @return Movie
 */
fun MovieEntity.mapToMovie(): Movie =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage
    )


/**
 * Map MovieResponse object to Movie object
 *
 * @return Movie
 */
fun MovieResponse.mapToMovie(): Movie =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage
    )

/**
 * Map Movie object to MovieEntity
 *
 * @return MovieEntity
 */
fun Movie.mapToMovieEntity(): MovieEntity =
    MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath.toString(),
        voteAverage = this.voteAverage
    )

/**
 * Map CastResponse object to Cast object
 *
 * return Cast
 */
fun CastResponse.mapToCast(): Cast =
    Cast(
        id = this.id,
        name = this.name,
        character = this.character,
        profilePath = this.profilePath
    )