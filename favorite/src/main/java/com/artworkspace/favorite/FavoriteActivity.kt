package com.artworkspace.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.ui.MovieVerticalAdapter
import com.artworkspace.core.utils.setVisibility
import com.artworkspace.favorite.databinding.ActivityFavoriteBinding
import com.artworkspace.themovie.di.FavoriteModuleDependency
import com.artworkspace.themovie.view.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependency::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        parseFavoriteMovies()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Fetch all favorite movies data and parse it to the related views
     */
    private fun parseFavoriteMovies() {
        supportActionBar?.title = getString(com.artworkspace.themovie.R.string.favorite)
        viewModel.favoriteMovies.observe(this) { movies ->
            if (movies.isNotEmpty()) {
                val layoutManager = LinearLayoutManager(this)
                val adapter = MovieVerticalAdapter(movies)
                adapter.setOnItemClickCallback(object : MovieVerticalAdapter.OnItemClickCallback {
                    override fun onItemClicked(movie: Movie) {
                        Intent(this@FavoriteActivity, DetailActivity::class.java).also { intent ->
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

                showErrorMessage(false)

            } else {
                showErrorMessage(
                    true,
                    getString(com.artworkspace.themovie.R.string.there_is_no_data)
                )
            }

            binding.shimmerRv.setVisibility(false)

        }
    }

    private fun showErrorMessage(isVisible: Boolean, message: String? = null) {
        binding.apply {
            rvList.visibility = if (isVisible) View.GONE else View.VISIBLE
            mainMessage.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvMessage.text = message
        }
    }

}