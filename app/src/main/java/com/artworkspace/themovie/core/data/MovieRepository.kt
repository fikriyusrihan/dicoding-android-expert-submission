package com.artworkspace.themovie.core.data

import com.artworkspace.themovie.core.data.source.remote.network.ApiService
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import kotlinx.coroutines.flow.Flow
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
        try {
            val response = apiService.getNowPlayingMovies(region)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }


    /**
     * Provide a flow with trending movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getTrendingMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        try {
            val response = apiService.getTrendingMovies(region)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }


    /**
     * Provide a flow with upcoming movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getUpcomingMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        try {
            val response = apiService.getUpcomingMovies(region)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }


    /**
     * Provide a flow with popular movies data
     *
     * @param region User region
     * @return Flow
     */
    fun getPopularMovies(region: String): Flow<Result<ListMovieResponse>> = flow {
        try {
            val response = apiService.getPopularMovies(region)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }
}