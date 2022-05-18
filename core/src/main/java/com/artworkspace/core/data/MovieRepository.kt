package com.artworkspace.core.data

import com.artworkspace.core.data.source.local.room.MovieDao
import com.artworkspace.core.data.source.remote.network.ApiService
import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.domain.repository.IMovieRepository
import com.artworkspace.core.utils.mapToCast
import com.artworkspace.core.utils.mapToMovie
import com.artworkspace.core.utils.mapToMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : IMovieRepository {

    /**
     * Provide a flow with now playing movies data
     *
     * @param region User region
     * @return Flow
     */
    override fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getNowPlayingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with trending movies data
     *
     * @param region User region
     * @return Flow
     */
    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getTrendingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with upcoming movies data
     *
     * @param region User region
     * @return Flow
     */
    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getUpcomingMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with popular movies data
     *
     * @param region User region
     * @return Flow
     */
    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getPopularMovies(region).results

        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with related movies that related to a movie
     *
     * @param id Movie ID
     * @return Flow
     */
    override fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>> = flow {
        val response = apiService.getRelatedMovie(id).results
        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }


    /**
     * Provide a flow with related casts into a movie
     *
     * @param id Movie ID
     * @return Flow
     */
    override fun getMovieCasts(id: Int): Flow<Result<List<Cast>>> = flow {
        val response = apiService.getMovieCasts(id).cast
        val casts = mutableListOf<Cast>()
        response.forEach { castResponse ->
            casts.add(castResponse.mapToCast())
        }
        emit(Result.success(casts))
    }.catch { e ->
        emit(Result.failure(e))
    }

    /**
     * Provide a flow with list of movie based on a query
     *
     * @param query Search query
     * @return Flow
     */
    override fun getMovieByQuery(query: String): Flow<Result<List<Movie>>> = flow {
        val encodedQuery = URLEncoder.encode(query, "utf-8")
        val response = apiService.getMovieByQuery(encodedQuery).results
        val movies = mutableListOf<Movie>()
        response?.forEach { movieResponse ->
            movies.add(movieResponse.mapToMovie())
        }

        emit(Result.success(movies))
    }.catch { e ->
        emit(Result.failure(e))
    }

    /**
     * Get all favorite movies from datasource
     *
     * @return Flow
     */
    override fun getAllFavoriteMovies(): Flow<List<Movie>> = flow {
        movieDao.getAllFavoriteMovies().collect { movieEntities ->
            val movies = mutableListOf<Movie>()
            movieEntities.forEach { movieEntity ->
                movies.add(movieEntity.mapToMovie())
            }

            emit(movies)
        }
    }

    /**
     * Determine the favorite status of a movie based on movie id
     *
     * @param id MovieID
     * @return Flow
     */
    override fun isFavoriteMovie(id: Int): Flow<Boolean> = movieDao.isFavoriteMovie(id)

    /**
     * Save a movie as favorite to the database
     *
     * @param movie Movie
     */
    override suspend fun saveMovieAsFavorite(movie: Movie) {
        movieDao.saveMovieAsFavorite(movie.mapToMovieEntity())
    }

    /**
     * Delete a movie from the favorite list based on movie ID
     *
     * @param id Movie ID
     */
    override suspend fun deleteMovieFromFavorite(id: Int) {
        movieDao.deleteMovieFromFavorite(id)
    }
}