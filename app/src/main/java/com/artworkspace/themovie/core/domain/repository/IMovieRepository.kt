package com.artworkspace.themovie.core.domain.repository

import com.artworkspace.themovie.core.domain.model.Cast
import com.artworkspace.themovie.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
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