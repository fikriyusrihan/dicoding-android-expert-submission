package com.artworkspace.themovie.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    /**
     * LiveData of now playing movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getNowPlayingMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getNowPlayingMovies(region).asLiveData()


    /**
     * LiveData of trending movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getTrendingMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getTrendingMovies(region).asLiveData()


    /**
     * LiveData of upcoming movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getUpcomingMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getUpcomingMovies(region).asLiveData()


    /**
     * LiveData of popular movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getPopularMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getPopularMovies(region).asLiveData()
}