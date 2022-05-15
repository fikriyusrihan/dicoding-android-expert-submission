package com.artworkspace.themovie.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    /**
     * Provide all favorite movies data as LiveData
     *
     * @return LiveData
     */
    fun getAllFavoriteMovies(): LiveData<List<Movie>> =
        movieUseCase.getAllFavoriteMovies().asLiveData()

    fun getAllTrendingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getTrendingMovies(region).asLiveData()

    fun getAllUpcomingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getUpcomingMovies(region).asLiveData()

    fun getAllPopularMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getPopularMovies(region).asLiveData()
}