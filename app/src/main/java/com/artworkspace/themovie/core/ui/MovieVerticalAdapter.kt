package com.artworkspace.themovie.core.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.themovie.core.data.source.local.entity.MovieEntity
import com.artworkspace.themovie.core.utils.getImageUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.core.utils.setImageFromUrl
import com.artworkspace.themovie.databinding.ItemMovieVerticalBinding
import com.artworkspace.themovie.view.detail.DetailActivity
import com.artworkspace.themovie.view.detail.DetailActivity.Companion.EXTRA_MOVIE_DETAIL

class MovieVerticalAdapter(private val movies: List<MovieEntity>) :
    RecyclerView.Adapter<MovieVerticalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieVerticalAdapter.ViewHolder {
        val binding =
            ItemMovieVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieVerticalAdapter.ViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.itemView.setOnClickListener {
            Intent(context, DetailActivity::class.java).also { intent ->
                intent.putExtra(EXTRA_MOVIE_DETAIL, movie.id)
                context.startActivity(intent)
            }
        }

        holder.bind(context, movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, movie: MovieEntity) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                tvMovieItemTitle.text = movie.title
                tvMovieItemRating.text = String.format("%.2f", rating)
                tvMovieItemOverview.text = movie.overview

                ivMovieItemRating.parseMovieRating(rating.toInt())
                ivMovieItemPoster.setImageFromUrl(context, getImageUrl(movie.posterPath))
            }
        }
    }
}