package com.artworkspace.themovie.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.themovie.core.domain.model.Movie
import com.artworkspace.themovie.core.utils.getImageUrl
import com.artworkspace.themovie.core.utils.parseMovieRating
import com.artworkspace.themovie.core.utils.setImageFromUrl
import com.artworkspace.themovie.databinding.ItemMovieVerticalBinding

class MovieVerticalAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieVerticalAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    /**
     * Set an item click callback
     *
     * @param onItemClickCallback object that implements onItemClickCallback
     */
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
        holder.bind(context, movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, movie: Movie) {
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

    interface OnItemClickCallback {
        fun onItemClicked(movie: Movie)
    }
}