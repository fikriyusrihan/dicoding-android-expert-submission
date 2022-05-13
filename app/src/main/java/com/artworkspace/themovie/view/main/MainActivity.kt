package com.artworkspace.themovie.view.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.ui.MovieHorizontalAdapter
import com.artworkspace.themovie.core.utils.animateAlpha
import com.artworkspace.themovie.core.utils.getImageOriginalUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.databinding.ActivityMainBinding
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
class MainActivity : AppCompatActivity() {

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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_manu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_favorite -> {
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
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

        mainViewModel.getNowPlayingMovies(region).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null) {
                    val resultLength = response.results.size
                    val seed = Calendar.getInstance().timeInMillis

                    val index = Random(seed).nextInt(0, resultLength)
                    val movie = response.results[index]

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
                    }
                }
            }
        }

        mainViewModel.getTrendingMovies(region).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null) {
                    val movies = response.results
                    val adapter = MovieHorizontalAdapter(movies)

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
        }

        mainViewModel.getUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null) {
                    val movies = response.results
                    val adapter = MovieHorizontalAdapter(movies)

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
        }

        mainViewModel.getPopularMovies(region).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null) {
                    val movies = response.results
                    val adapter = MovieHorizontalAdapter(movies)

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
        }
    }
}