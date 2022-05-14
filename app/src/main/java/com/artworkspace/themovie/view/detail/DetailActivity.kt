package com.artworkspace.themovie.view.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.ui.MovieCastAdapter
import com.artworkspace.themovie.core.ui.MovieHorizontalAdapter
import com.artworkspace.themovie.core.utils.animateAlpha
import com.artworkspace.themovie.core.utils.getImageOriginalUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val id = intent.getIntExtra(EXTRA_MOVIE_DETAIL, 0)
        fetchAllData(id)

        binding.toggleFavorite.setOnCheckedChangeListener { _, b ->
            Toast.makeText(this, b.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Fetch all required data from the ViewModel
     *
     * @param id Movie ID
     */
    private fun fetchAllData(id: Int) {
        detailViewModel.getRelatedMovies(id).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null && response.results.isNotEmpty()) {
                    val movies = response.results
                    val adapter = MovieHorizontalAdapter(movies)

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvRelatedMovies
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

                    binding.apply {
                        tvRelatedMoviesTitle.animateAlpha(true)
                        rvRelatedMovies.animateAlpha(true)
                    }
                }
            }
        }

        detailViewModel.getMovieCasts(id).observe(this) { result ->
            result.onSuccess { response ->
                val casts = response.cast.slice(0..5)
                val adapter = MovieCastAdapter(casts)
                val layoutManager = LinearLayoutManager(this@DetailActivity)

                val recyclerView = binding.rvDetailCast
                recyclerView.apply {
                    this.adapter = adapter
                    this.setHasFixedSize(true)
                    this.layoutManager = layoutManager
                }

                binding.apply {
                    tvCastTitle.animateAlpha(true)
                    rvDetailCast.animateAlpha(true)
                }
            }
        }

        detailViewModel.getMovieDetail(id).observe(this) { response ->
            response.onSuccess { movie ->
                Log.d(TAG, "onCreate: $movie")
                val rating = movie.voteAverage.div(2)

                binding.apply {
                    tvMovieTitle.text = movie.title
                    tvMovieRating.text = String.format("%.2f", rating)
                    tvMovieOverview.text = movie.overview

                    ivMovieRating.parseMovieRating(rating.toInt())

                    Glide
                        .with(this@DetailActivity)
                        .load(getImageOriginalUrl(movie.posterPath))
                        .error(R.drawable.image_load_error)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                shimmerMainPoster.stopShimmer()
                                shimmerMainPoster.animateAlpha(false)
                                tvOverviewTitle.animateAlpha(true)
                                tvMovieOverview.animateAlpha(true)
                                tvMovieTitle.animateAlpha(true)
                                llRatingView.animateAlpha(true)

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
                                tvOverviewTitle.animateAlpha(true)
                                tvMovieOverview.animateAlpha(true)
                                tvMovieTitle.animateAlpha(true)
                                llRatingView.animateAlpha(true)

                                return false
                            }
                        })
                        .into(ivMoviePoster)
                }
            }

            response.onFailure {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"
        private const val TAG = "DetailActivity"
    }
}