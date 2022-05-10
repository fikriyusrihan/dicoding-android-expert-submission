package com.artworkspace.themovie.core.data.source.remote.network

import com.artworkspace.themovie.BuildConfig.API_KEY
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Call the API that provide all now playing movies data
     *
     * @param apiKey TheMovieDB API key
     * @return ListMovieResponse
     */
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse


    /**
     * Call the API that provide all trending movies data
     *
     * @param apiKey TheMovieDB API key
     * @return ListMovieResponse
     */
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse


    /**
     * Call the API that provide all upcoming movies data
     *
     * @param apiKey TheMovieDB API key
     * @return ListMovieResponse
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse


    /**
     * Call the API that provide all popular movies data
     *
     * @param apiKey TheMovieDB API key
     * @return ListMovieResponse
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse


}