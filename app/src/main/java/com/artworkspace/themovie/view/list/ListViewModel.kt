package com.artworkspace.themovie.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    /**
     * Provide all favorite movies data as LiveData
     *
     * @return LiveData
     */
    fun getAllFavoriteMovies(): LiveData<List<MovieEntity>> =
        movieRepository.getAllFavoriteMovies().asLiveData()

    fun getAllTrendingMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getTrendingMovies(region).asLiveData()

    fun getAllUpcomingMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getUpcomingMovies(region).asLiveData()

    fun getAllPopularMovies(region: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getPopularMovies(region).asLiveData()
}