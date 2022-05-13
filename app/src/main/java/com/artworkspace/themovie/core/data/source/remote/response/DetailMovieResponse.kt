package com.artworkspace.themovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
