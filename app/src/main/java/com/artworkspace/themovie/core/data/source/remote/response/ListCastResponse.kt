package com.artworkspace.themovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListCastResponse(

    @field:SerializedName("cast")
    val cast: List<Cast>,

    @field:SerializedName("id")
    val id: Int
)

data class Cast(

    @field:SerializedName("character")
    val character: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String,

    @field:SerializedName("id")
    val id: Int,
)
