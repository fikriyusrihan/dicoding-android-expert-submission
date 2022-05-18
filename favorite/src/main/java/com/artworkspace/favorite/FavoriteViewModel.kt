package com.artworkspace.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovies = movieUseCase.getAllFavoriteMovies().asLiveData()
}