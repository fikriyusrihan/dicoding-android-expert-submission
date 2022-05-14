package com.artworkspace.themovie.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.artworkspace.themovie.core.data.MovieRepository
import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
import com.artworkspace.themovie.core.data.source.remote.response.DetailMovieResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListCastResponse
import com.artworkspace.themovie.core.data.source.remote.response.ListMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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


    /**
     * Save a movie as favorite to the database
     *
     * @param id Favorite movie id
     */
    fun saveMovieAsFavorite(id: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetail(id).collect { result ->
                result.onSuccess { movieResponse ->
                    val movie = MovieEntity(
                        id = movieResponse.id,
                        title = movieResponse.title,
                        overview = movieResponse.overview,
                        posterPath = movieResponse.posterPath,
                        voteAverage = movieResponse.voteAverage
                    )
                    movieRepository.saveMovieAsFavorite(movie)
                }
            }
        }
    }

    fun deleteMovieFromFavorite(id: Int) {
        viewModelScope.launch {
            movieRepository.deleteMovieFromFavorite(id)
        }
    }

    fun isFavoriteMovie(id: Int): LiveData<Boolean> =
        movieRepository.isFavoriteMovie(id).asLiveData()
}