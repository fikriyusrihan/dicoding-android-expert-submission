package com.artworkspace.themovie.view.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.ui.MovieHorizontalAdapter
import com.artworkspace.themovie.core.utils.animateAlpha
import com.artworkspace.themovie.core.utils.getImageOriginalUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.databinding.ActivityMainBinding
import com.artworkspace.themovie.view.detail.DetailActivity
import com.artworkspace.themovie.view.detail.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.artworkspace.themovie.view.list.ListActivity
import com.artworkspace.themovie.view.list.ListActivity.Companion.CATEGORY_FAVORITE
import com.artworkspace.themovie.view.list.ListActivity.Companion.CATEGORY_POPULAR
import com.artworkspace.themovie.view.list.ListActivity.Companion.CATEGORY_TRENDING
import com.artworkspace.themovie.view.list.ListActivity.Companion.CATEGORY_UPCOMING
import com.artworkspace.themovie.view.list.ListActivity.Companion.EXTRA_LIST_CATEGORY
import com.artworkspace.themovie.view.search.SearchActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setActivityFullScreen()
        getMovies()

        binding.apply {
            tvTrendingMore.setOnClickListener(this@MainActivity)
            tvUpcomingMore.setOnClickListener(this@MainActivity)
            tvPopularMore.setOnClickListener(this@MainActivity)
            btnErrorRetry.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_trending_more -> {
                Intent(this, ListActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_TRENDING)
                    startActivity(intent)
                }
            }
            R.id.tv_upcoming_more -> {
                Intent(this, ListActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_UPCOMING)
                    startActivity(intent)
                }
            }
            R.id.tv_popular_more -> {
                Intent(this, ListActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_POPULAR)
                    startActivity(intent)
                }
            }
            R.id.btn_error_retry -> {
                getMovies()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_manu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Intent(this, SearchActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                true
            }
            R.id.menu_favorite -> {
                Intent(this, ListActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_FAVORITE)
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Set activity to fullscreen
     */
    private fun setActivityFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    /**
     * Get all required data by observing the related LiveData
     * and parse the data to the related views
     */
    private fun getMovies() {
        val region = ConfigurationCompat.getLocales(resources.configuration)[0].country

        binding.apply {
            mainErrorMessage.visibility = View.GONE
            mainScrollView.visibility = View.VISIBLE
        }

        mainViewModel.getNowPlayingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val resultLength = movies.size
                    val seed = Calendar.getInstance().timeInMillis

                    val index = Random(seed).nextInt(0, resultLength)
                    val movie = movies[index]

                    binding.apply {
                        tvMainTitle.text = movie.title
                        tvMainRating.text = (movie.voteAverage.div(2)).toString()
                        tvMainInformation.text = movie.overview

                        ivMainRating.parseMovieRating(movie.voteAverage.div(2).toInt())

                        Glide
                            .with(this@MainActivity)
                            .load(getImageOriginalUrl(movie.posterPath))
                            .error(R.drawable.image_load_error)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

                                    shimmerMainPoster.stopShimmer()
                                    shimmerMainPoster.animateAlpha(false)
                                    tvMainTitle.animateAlpha(true)
                                    tvMainInformation.animateAlpha(true)
                                    llRatingView.animateAlpha(true)

                                    return false
                                }
                            })
                            .into(ivMainPoster)

                        ivMainPoster.setOnClickListener {
                            Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getTrendingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MovieHorizontalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieHorizontalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvTrending
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRvTrending.apply {
                        stopShimmer()
                        animateAlpha(false)
                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MovieHorizontalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieHorizontalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvUpcoming
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRvUpcoming.apply {
                        stopShimmer()
                        animateAlpha(false)
                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getPopularMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MovieHorizontalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieHorizontalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvPopular
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

                    binding.shimmerRvPopular.apply {
                        stopShimmer()
                        animateAlpha(false)
                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }
    }


    /**
     * Set UI error state when an error occurred
     */
    private fun onErrorOccurred() {
        binding.apply {
            mainErrorMessage.visibility = View.VISIBLE
            mainScrollView.visibility = View.GONE
        }
    }
}