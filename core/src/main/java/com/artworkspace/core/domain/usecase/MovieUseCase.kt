package com.artworkspace.core.domain.usecase

import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>>

    fun getTrendingMovies(region: String): Flow<Result<List<Movie>>>

    fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>>

    fun getPopularMovies(region: String): Flow<Result<List<Movie>>>

    fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>>

    fun getMovieCasts(id: Int): Flow<Result<List<Cast>>>

    fun getMovieByQuery(query: String): Flow<Result<List<Movie>>>

    fun getAllFavoriteMovies(): Flow<List<Movie>>

    fun isFavoriteMovie(id: Int): Flow<Boolean>

    suspend fun saveMovieAsFavorite(movie: Movie)

    suspend fun deleteMovieFromFavorite(id: Int)
}