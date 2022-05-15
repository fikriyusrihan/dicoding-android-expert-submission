package com.artworkspace.themovie.view.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.themovie.R
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.ui.MovieVerticalAdapter
import com.artworkspace.themovie.core.utils.mapToMovie
import com.artworkspace.themovie.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.movie_title)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                onBackPressed()
                return false
            }

        })

        return true
    }

    private fun searchMovie(query: String) {
        binding.pbLoading.visibility = View.VISIBLE
        viewModel.searchMovieByQuery(query).observe(this) { result ->
            result.onSuccess { response ->
                if (response.results != null) {
                    val list = mutableListOf<Movie>()
                    response.results.forEach { movieResponse ->
                        list.add(movieResponse.mapToMovie())
                    }

                    val adapter = MovieVerticalAdapter(list)
                    val layoutManager = LinearLayoutManager(this)

                    val recyclerView = binding.rvMovies
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }
                }
                binding.pbLoading.visibility = View.GONE
            }
        }
    }
}