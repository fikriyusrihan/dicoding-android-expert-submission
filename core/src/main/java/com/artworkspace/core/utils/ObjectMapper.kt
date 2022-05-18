package com.artworkspace.core.utils

import com.artworkspace.core.data.source.local.entity.MovieEntity
import com.artworkspace.core.data.source.remote.response.CastResponse
import com.artworkspace.core.data.source.remote.response.MovieResponse
import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie

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