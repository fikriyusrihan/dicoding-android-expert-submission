package com.artworkspace.themovie.core.data.source.remote.network

import com.artworkspace.themovie.BuildConfig.API_KEY
import com.artworkspace.themovie.core.data.source.remote.response.DetailMovieResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListCastResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
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


    /**
     * Call the API that provide detail information about a movie based on id
     *
     * @param id Movie ID
     * @param apiKey TheMovieDB API key
     * @return DetailMovieResponse
     */
    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): DetailMovieResponse

    /**
     * Call the API that provide another recommended movie with another movie
     *
     * @param id MovieID
     * @param apiKey TheMovieDB API Key
     * @return ListMovieResponse
     */
    @GET("movie/{movie_id}/recommendations")
    suspend fun getRelatedMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCasts(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListCastResponse
}