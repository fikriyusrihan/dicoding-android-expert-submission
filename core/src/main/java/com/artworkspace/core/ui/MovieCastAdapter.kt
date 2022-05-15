package com.artworkspace.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artworkspace.core.databinding.ItemMovieCastBinding
import com.artworkspace.core.domain.model.Cast
import com.artworkspace.core.utils.getImageUrl
import com.artworkspace.core.utils.setImageFromUrl

class MovieCastAdapter(private val casts: List<Cast>) :
    RecyclerView.Adapter<MovieCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = casts[position]
        val context = holder.itemView.context

        holder.bind(context, cast)
    }

    override fun getItemCount(): Int = casts.size

    inner class ViewHolder(private val binding: ItemMovieCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, cast: Cast) {
            binding.apply {
                tvCastName.text = cast.name
                tvCastRole.text = cast.character

                ivCastProfile.setImageFromUrl(context, getImageUrl(cast.profilePath))
            }
        }
    }
}