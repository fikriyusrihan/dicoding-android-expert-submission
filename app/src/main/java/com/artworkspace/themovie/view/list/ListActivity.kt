package com.artworkspace.themovie.view.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.ui.MovieVerticalAdapter
import com.artworkspace.core.utils.setVisibility
import com.artworkspace.themovie.R
import com.artworkspace.themovie.databinding.ActivityListBinding
import com.artworkspace.themovie.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel: ListViewModel by viewModels()
    private var region: String = "US"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        region = ConfigurationCompat.getLocales(resources.configuration)[0].country

        when (intent.getStringExtra(EXTRA_LIST_CATEGORY)) {
            CATEGORY_TRENDING -> {
                parseTrendingMovies()
            }
            CATEGORY_UPCOMING -> {
                parseUpcomingMovies()
            }
            CATEGORY_POPULAR -> {
                parsePopularMovies()
            }
            else -> {
                Toast.makeText(this, getString(R.string.invalid_category), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Fetch all popular movies data and parse it to the related views
     */
    private fun parsePopularMovies() {
        supportActionBar?.title = getString(R.string.popular)
        viewModel.getAllPopularMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MovieVerticalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieVerticalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.there_is_no_data))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.an_error_occurred))
            }
        }
    }

    /**
     * Fetch all upcoming movies data and parse it to the related views
     */
    private fun parseUpcomingMovies() {
        supportActionBar?.title = getString(R.string.upcoming)
        viewModel.getAllUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MovieVerticalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieVerticalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.there_is_no_data))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.an_error_occurred))
            }
        }
    }


    /**
     * Fetch all trending movies data and parse it to the related views
     */
    private fun parseTrendingMovies() {
        supportActionBar?.title = getString(R.string.trending)
        viewModel.getAllTrendingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MovieVerticalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieVerticalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.there_is_no_data))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.an_error_occurred))
            }
        }
    }

    private fun showErrorMessage(isVisible: Boolean, message: String? = null) {
        binding.apply {
            rvList.visibility = if (isVisible) View.GONE else View.VISIBLE
            mainMessage.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvMessage.text = message
        }
    }

    companion object {
        const val EXTRA_LIST_CATEGORY = "extra_list_category"
        const val CATEGORY_POPULAR = "popular"
        const val CATEGORY_TRENDING = "trending"
        const val CATEGORY_UPCOMING = "upcoming"
    }
}