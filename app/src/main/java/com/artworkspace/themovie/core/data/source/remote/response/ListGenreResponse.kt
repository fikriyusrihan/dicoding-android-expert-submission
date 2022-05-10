package com.artworkspace.themovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListGenreResponse(

    @field:SerializedName("genres")
    val genres: List<Genre>
)

data class Genre(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
