package com.artworkspace.themovie.view.list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.ui.MovieVerticalAdapter
import com.artworkspace.themovie.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        when (intent.getStringExtra(EXTRA_LIST_CATEGORY)) {
            "favorite" -> {
                parseFavoriteMovies()
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
     * Fetch all favorite movies data and parse it to the related views
     */
    private fun parseFavoriteMovies() {
        supportActionBar?.title = getString(R.string.favorite)
        viewModel.getAllFavoriteMovie().observe(this) { movies ->
            val layoutManager = LinearLayoutManager(this)
            val adapter = MovieVerticalAdapter(movies)

            val recyclerView = binding.rvList
            recyclerView.apply {
                this.adapter = adapter
                this.layoutManager = layoutManager
            }
        }
    }

    companion object {
        const val EXTRA_LIST_CATEGORY = "extra_list_category"
    }
}