package com.artworkspace.themovie.view.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.domain.model.Movie
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
class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var movie: Movie
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

        movie = intent.getParcelableExtra(EXTRA_MOVIE_DETAIL)!!
        fetchAllData(movie.id)
        parseDetailMovieInformation()

        binding.toggleFavorite.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.toggle_favorite -> {
                val isChecked = binding.toggleFavorite.isChecked
                if (isChecked) {
                    detailViewModel.saveMovieAsFavorite(movie)
                    Toast.makeText(this, getString(R.string.saved_as_favorite), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    detailViewModel.deleteMovieFromFavorite(movie.id)
                    Toast.makeText(
                        this,
                        getString(R.string.deleted_from_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MovieHorizontalAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MovieHorizontalAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@DetailActivity, DetailActivity::class.java).also { intent ->
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
            result.onSuccess { casts ->
                Log.d(TAG, "fetchAllData: $casts")
                if (casts.isNotEmpty()) {
                    val selectedCasts =
                        casts.slice(if (casts.size <= 5) casts.indices else 0 until 5)
                    val adapter = MovieCastAdapter(selectedCasts)
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

            result.onFailure {
                Log.e(TAG, "fetchAllData: ${it.message}")
            }
        }

        detailViewModel.isFavoriteMovie(id).observe(this) { isFavorite ->
            binding.toggleFavorite.isChecked = isFavorite
        }
    }

    private fun parseDetailMovieInformation() {
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

                        Toast.makeText(
                            this@DetailActivity,
                            getString(R.string.an_error_occurred),
                            Toast.LENGTH_SHORT
                        )
                            .show()

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

    companion object {
        const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"
        private const val TAG = "DetailActivity"
    }
}