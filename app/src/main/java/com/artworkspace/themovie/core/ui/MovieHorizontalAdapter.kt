package com.artworkspace.themovie.core.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.themovie.core.data.source.remote.response.MovieResponse
import com.artworkspace.themovie.core.utils.getImageUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.core.utils.setImageFromUrl
import com.artworkspace.themovie.databinding.ItemMovieHorizontalBinding
import com.artworkspace.themovie.view.detail.DetailActivity
import com.artworkspace.themovie.view.detail.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager


class MovieHorizontalAdapter(private val movies: List<MovieResponse>) :
    RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    inner class ViewHolder(private val binding: ItemMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val lp: ViewGroup.LayoutParams = itemView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexShrink = 0.0f
                lp.alignSelf = AlignItems.FLEX_START
            }
        }

        fun bind(context: Context, movie: MovieResponse) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                tvMovieItemTitle.text = movie.title
                tvMovieItemRating.text = String.format("%.2f", rating)

                ivMovieItemStar.parseMovieRating(rating.toInt())
                ivMovieItemPoster.setImageFromUrl(
                    context,
                    getImageUrl(movie.posterPath)
                )
            }
        }
    }
}