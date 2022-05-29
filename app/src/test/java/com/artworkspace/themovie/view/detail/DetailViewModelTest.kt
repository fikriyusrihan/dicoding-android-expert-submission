package com.artworkspace.themovie.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.domain.usecase.MovieInteractor
import com.artworkspace.themovie.utils.CoroutinesTestRule
import com.artworkspace.themovie.utils.DataDummy
import com.artworkspace.themovie.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var detailViewModel: DetailViewModel

    private val dummyId = 1
    private val dummyMovies = DataDummy.generateMovies()
    private val moviesInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val exceptionInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(Exception("failed")))

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(movieInteractor)
    }

    @Test
    fun `Get related movies - successfully`() {
        Mockito.`when`(movieInteractor.getRelatedMovies(dummyId)).thenReturn(moviesInFlow)

        detailViewModel.getRelatedMovies(dummyId).getOrAwaitValue().also { actualResult ->
            Mockito.verify(movieInteractor).getRelatedMovies(dummyId)
            Assert.assertNotNull(actualResult)
            Assert.assertTrue(actualResult.isSuccess)
            Assert.assertFalse(actualResult.isFailure)

            actualResult.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get related movies - failed`() = runTest {
        Mockito.`when`(movieInteractor.getRelatedMovies(dummyId)).thenReturn(exceptionInFlow)

        detailViewModel.getRelatedMovies(dummyId).getOrAwaitValue().also { actualResponse ->
            Mockito.verify(movieInteractor).getRelatedMovies(dummyId)
            Assert.assertNotNull(actualResponse)
            Assert.assertFalse(actualResponse.isSuccess)
            Assert.assertTrue(actualResponse.isFailure)

            actualResponse.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }

    @Test
    fun `Get casts of the movie`() = runTest {
        Mockito.`when`(movieInteractor.getMovieCasts(dummyId))
            .thenReturn(flowOf(Result.success(DataDummy.generateMovieCast())))

        detailViewModel.getMovieCasts(dummyId).getOrAwaitValue().also { actualResponse ->
            Mockito.verify(movieInteractor).getMovieCasts(dummyId)
            Assert.assertNotNull(actualResponse)
            Assert.assertTrue(actualResponse.isSuccess)
            Assert.assertFalse(actualResponse.isFailure)

            actualResponse.onSuccess { casts ->
                Assert.assertNotNull(casts)

                casts.forEachIndexed { index, cast ->
                    Assert.assertEquals(DataDummy.generateMovieCast()[index].name, cast.name)
                }
            }
        }
    }

    @Test
    fun `Save movie as favorite`() = runTest {
        val movie = DataDummy.generateMovies().first()

        detailViewModel.saveMovieAsFavorite(movie).also {
            Mockito.verify(movieInteractor).saveMovieAsFavorite(movie)
        }
    }

    @Test
    fun `Delete movie from favorite`() = runTest {
        val movie = DataDummy.generateMovies().first()

        detailViewModel.deleteMovieFromFavorite(movie.id).also {
            Mockito.verify(movieInteractor).deleteMovieFromFavorite(movie.id)
        }
    }

    @Test
    fun `Get movie's favorite state`() = runTest {
        val movie = DataDummy.generateMovies().first()
        val isFavorite = flowOf(true)

        Mockito.`when`(movieInteractor.isFavoriteMovie(movie.id)).thenReturn(isFavorite)

        detailViewModel.isFavoriteMovie(movie.id).getOrAwaitValue().also { actualResponse ->
            Mockito.verify(movieInteractor).isFavoriteMovie(movie.id)
            Assert.assertNotNull(actualResponse)
            Assert.assertTrue(actualResponse)
        }
    }
}