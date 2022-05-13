package com.artworkspace.themovie.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.remote.response.DetailMovieResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListCastResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    /**
     * LiveData of movie detail information
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun getMovieDetail(id: Int): LiveData<Result<DetailMovieResponse>> =
        movieRepository.getMovieDetail(id).asLiveData()

    /**
     * LiveData of related movie
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun getRelatedMovies(id: Int): LiveData<Result<ListMovieResponse>> =
        movieRepository.getRelatedMovies(id).asLiveData()


    /**
     * LiveData of movie's casts
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun getMovieCasts(id: Int): LiveData<Result<ListCastResponse>> =
        movieRepository.getMovieCasts(id).asLiveData()
}