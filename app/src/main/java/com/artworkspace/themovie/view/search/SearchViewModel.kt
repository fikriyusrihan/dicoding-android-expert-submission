package com.artworkspace.themovie.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    fun searchMovieByQuery(query: String): LiveData<Result<ListMovieResponse>> =
        movieRepository.getMovieByQuery(query).asLiveData()
}