package com.artworkspace.themovie.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
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
    fun getAllFavoriteMovie(): LiveData<List<MovieEntity>> =
        movieRepository.getAllFavoriteMovies().asLiveData()
}