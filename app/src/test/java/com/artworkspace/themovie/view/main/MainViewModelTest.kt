package com.artworkspace.themovie.view.main

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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var mainViewModel: MainViewModel

    private val region = "US"
    private val dummyMovies = DataDummy.generateMovies()
    private val moviesInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val exceptionInFlow: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(Exception("failed")))

    @Before
    fun setup() {
        mainViewModel = MainViewModel(movieInteractor)
    }

    @Test
    fun `Get now playing movies - successfully`() = runTest {
        `when`(movieInteractor.getNowPlayingMovies(region)).thenReturn(moviesInFlow)

        mainViewModel.getNowPlayingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getNowPlayingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isSuccess)
            Assert.assertFalse(actualMovies.isFailure)

            actualMovies.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get now playing movies - failed`() = runTest {
        `when`(movieInteractor.getNowPlayingMovies(region)).thenReturn(exceptionInFlow)

        mainViewModel.getNowPlayingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getNowPlayingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertFalse(actualMovies.isSuccess)
            Assert.assertTrue(actualMovies.isFailure)

            actualMovies.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }

    @Test
    fun `Get trending movies - successfully`() = runTest {
        `when`(movieInteractor.getTrendingMovies(region)).thenReturn(moviesInFlow)

        mainViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getTrendingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isSuccess)
            Assert.assertFalse(actualMovies.isFailure)

            actualMovies.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get trending movies - failed`() = runTest {
        `when`(movieInteractor.getTrendingMovies(region)).thenReturn(exceptionInFlow)

        mainViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getTrendingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertFalse(actualMovies.isSuccess)
            Assert.assertTrue(actualMovies.isFailure)

            actualMovies.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }

    @Test
    fun `Get upcoming movies - successfully`() = runTest {
        `when`(movieInteractor.getUpcomingMovies(region)).thenReturn(moviesInFlow)

        mainViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getUpcomingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isSuccess)
            Assert.assertFalse(actualMovies.isFailure)

            actualMovies.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get upcoming movies - failed`() = runTest {
        `when`(movieInteractor.getUpcomingMovies(region)).thenReturn(exceptionInFlow)

        mainViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getUpcomingMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertFalse(actualMovies.isSuccess)
            Assert.assertTrue(actualMovies.isFailure)

            actualMovies.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }

    @Test
    fun `Get popular movies - successfully`() = runTest {
        `when`(movieInteractor.getPopularMovies(region)).thenReturn(moviesInFlow)

        mainViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getPopularMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isSuccess)
            Assert.assertFalse(actualMovies.isFailure)

            actualMovies.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get popular movies - failed`() = runTest {
        `when`(movieInteractor.getPopularMovies(region)).thenReturn(exceptionInFlow)

        mainViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getPopularMovies(region)
            Assert.assertNotNull(actualMovies)
            Assert.assertFalse(actualMovies.isSuccess)
            Assert.assertTrue(actualMovies.isFailure)

            actualMovies.onFailure { e ->
                Assert.assertNotNull(e)
            }
        }
    }

}