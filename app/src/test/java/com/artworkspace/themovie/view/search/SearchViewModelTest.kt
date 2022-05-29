package com.artworkspace.themovie.view.search

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
class SearchViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var searchViewModel: SearchViewModel

    private val query = "movie"
    private val dummyMovies = DataDummy.generateMovies()
    private val moviesInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val exceptionInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(Exception("failed")))


    @Before
    fun setup() {
        searchViewModel = SearchViewModel(movieInteractor)
    }

    @Test
    fun `Get search result based on query - successfully`() = runTest {
        Mockito.`when`(movieInteractor.getMovieByQuery(query)).thenReturn(moviesInFlow)

        searchViewModel.searchMovieByQuery(query).getOrAwaitValue().also { actualResult ->
            Mockito.verify(movieInteractor).getMovieByQuery(query)
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
    fun `Get search result - failed`() = runTest {
        Mockito.`when`(movieInteractor.getMovieByQuery(query)).thenReturn(exceptionInFlow)

        searchViewModel.searchMovieByQuery(query).getOrAwaitValue().also { actualResponse ->
            Mockito.verify(movieInteractor).getMovieByQuery(query)
            Assert.assertNotNull(actualResponse)
            Assert.assertFalse(actualResponse.isSuccess)
            Assert.assertTrue(actualResponse.isFailure)

            actualResponse.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }
}