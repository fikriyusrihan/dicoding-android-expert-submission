package com.artworkspace.themovie.core.data

import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
import com.artworkspace.themovie.core.data.source.local.room.MovieDao
import com.artworkspace.themovie.core.data.source.remote.network.ApiService
import com.artworkspace.themovie.core.data.source.remote.response.ListCastResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import com.artworkspace.themovie.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.URLEncoder
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {

    /**
     * Provide a flow with now playing movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getNowPlayingMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        val response = apiService.getNowPlayingMovies(region)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with trending movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getTrendingMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        val response = apiService.getTrendingMovies(region)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with upcoming movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getUpcomingMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        val response = apiService.getUpcomingMovies(region)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with popular movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getPopularMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        val response = apiService.getPopularMovies(region)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with detail information about a movie based on id
     *
     * @param id Movie ID
     * @return Flow
     */
    fun getMovieDetail(id: Int): Flow<Result<MovieResponse>> = flow {
        val response = apiService.getDetailMovie(id)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with related movies that related to a movie
     *
     * @param id Movie ID
     * @return Flow
     */
    fun getRelatedMovies(id: Int): Flow<Result<ListMovieResponse>> = flow {
        val response = apiService.getRelatedMovie(id)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with related casts into a movie
     *
     * @param id Movie ID
     * @return Flow
     */
    fun getMovieCasts(id: Int): Flow<Result<ListCastResponse>> = flow {
        val response = apiService.getMovieCasts(id)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }

    /**
     * Provide a flow with list of movie based on a query
     *
     * @param query Search query
     * @return Flow
     */
    fun getMovieByQuery(query: String): Flow<Result<ListMovieResponse>> = flow {
        val encodedQuery = URLEncoder.encode(query, "utf-8")
        val response = apiService.getMovieByQuery(encodedQuery)
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }

    /**
     * Get all favorite movies from datasource
     *
     * @return Flow
     */
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getAllFavoriteMovies()

    /**
     * Determine the favorite status of a movie based on movie id
     *
     * @param id MovieID
     * @return Flow
     */
    fun isFavoriteMovie(id: Int): Flow<Boolean> = movieDao.isFavoriteMovie(id)

    /**
     * Save a movie as favorite to the database
     *
     * @param movie Movie
     */
    suspend fun saveMovieAsFavorite(movie: MovieEntity) {
        movieDao.saveMovieAsFavorite(movie)
    }

    /**
     * Delete a movie from the favorite list based on movie ID
     *
     * @param id Movie ID
     */
    suspend fun deleteMovieFromFavorite(id: Int) {
        movieDao.deleteMovieFromFavorite(id)
    }
}