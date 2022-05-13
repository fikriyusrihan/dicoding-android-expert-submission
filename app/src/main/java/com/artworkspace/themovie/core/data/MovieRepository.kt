package com.artworkspace.themovie.core.data

import com.artworkspace.themovie.core.data.source.remote.network.ApiService
import com.artworkspace.themovie.core.data.source.remote.response.DetailMovieResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListCastResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

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
    fun getMovieDetail(id: Int): Flow<Result<DetailMovieResponse>> = flow {
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
}