package com.artworkspace.themovie.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    /**
     * LiveData of related movie
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun getRelatedMovies(id: Int): LiveData<Result<List<Movie>>> =
        movieUseCase.getRelatedMovies(id).asLiveData()


    /**
     * LiveData of movie's casts
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun getMovieCasts(id: Int): LiveData<Result<List<Cast>>> =
        movieUseCase.getMovieCasts(id).asLiveData()


    /**
     * Save a movie as favorite to the database
     *
     * @param movie Movie
     */
    fun saveMovieAsFavorite(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.saveMovieAsFavorite(movie)
        }
    }


    /**
     * Delete a favorite movie from the database
     *
     * @param id Movie ID
     */
    fun deleteMovieFromFavorite(id: Int) {
        viewModelScope.launch {
            movieUseCase.deleteMovieFromFavorite(id)
        }
    }


    /**
     * Check a movie favorite status
     *
     * @param id Movie ID
     * @return LiveData
     */
    fun isFavoriteMovie(id: Int): LiveData<Boolean> =
        movieUseCase.isFavoriteMovie(id).asLiveData()
}