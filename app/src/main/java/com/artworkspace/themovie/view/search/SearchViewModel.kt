package com.artworkspace.themovie.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    fun searchMovieByQuery(query: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getMovieByQuery(query).asLiveData()
}