package com.artworkspace.core.domain.usecase

import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {
    override fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getNowPlayingMovies(region)

    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getTrendingMovies(region)

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getUpcomingMovies(region)

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getPopularMovies(region)

    override fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>> =
        movieRepository.getRelatedMovies(id)

    override fun getMovieCasts(id: Int): Flow<Result<List<Cast>>> =
        movieRepository.getMovieCasts(id)

    override fun getMovieByQuery(query: String): Flow<Result<List<Movie>>> =
        movieRepository.getMovieByQuery(query)

    override fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getAllFavoriteMovies()

    override fun isFavoriteMovie(id: Int): Flow<Boolean> =
        movieRepository.isFavoriteMovie(id)

    override suspend fun saveMovieAsFavorite(movie: Movie) =
        movieRepository.saveMovieAsFavorite(movie)

    override suspend fun deleteMovieFromFavorite(id: Int) =
        movieRepository.deleteMovieFromFavorite(id)
}