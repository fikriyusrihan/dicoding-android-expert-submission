package com.artworkspace.themovie.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.themovie.core.data.source.remote.response.Movie
import com.artworkspace.themovie.core.utils.getImageUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.core.utils.setImageFromUrl
import com.artworkspace.themovie.databinding.ItemMovieHorizontalBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager


class MovieHorizontalAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, movie.title, Toast.LENGTH_SHORT).show()
        }
        holder.bind(holder.itemView.context, movie)
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

        fun bind(context: Context, movie: Movie) {
            val rating = movie.voteAverage / 2
            binding.apply {
                tvMovieItemTitle.text = movie.title
                tvMovieItemRating.text = rating.toString()

                ivMovieItemStar.parseMovieRating(rating.toInt())
                ivMovieItemPoster.setImageFromUrl(
                    context,
                    getImageUrl(movie.posterPath)
                )
            }
        }
    }
}