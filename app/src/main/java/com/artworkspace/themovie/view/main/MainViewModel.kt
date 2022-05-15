package com.artworkspace.themovie.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    /**
     * LiveData of now playing movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getNowPlayingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getNowPlayingMovies(region).asLiveData()


    /**
     * LiveData of trending movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getTrendingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getTrendingMovies(region).asLiveData()


    /**
     * LiveData of upcoming movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getUpcomingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getUpcomingMovies(region).asLiveData()


    /**
     * LiveData of popular movies
     *
     * @param region User region
     * @return LiveData
     */
    fun getPopularMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getPopularMovies(region).asLiveData()
}