package com.artworkspace.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.core.databinding.ItemMovieHorizontalBinding
import com.artworkspace.core.domain.model.Movie
import com.artworkspace.core.utils.getImageUrl
import com.artworkspace.core.utils.parseMovieRating
import com.artworkspace.core.utils.setImageFromUrl
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager


class MovieHorizontalAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    /**
     * Set an item click callback
     *
     * @param onItemClickCallback object that implements onItemClickCallback
     */
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
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

        fun bind(context: Context, movie: Movie) {
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

    interface OnItemClickCallback {
        fun onItemClicked(movie: Movie)
    }
}