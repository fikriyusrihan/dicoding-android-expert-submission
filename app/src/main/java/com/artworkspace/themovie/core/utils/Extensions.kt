package com.artworkspace.themovie.core.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.artworkspace.themovie.BuildConfig.API_IMG_ENDPOINT
import com.artworkspace.themovie.BuildConfig.API_IMG_ORIGINAL_ENDPOINT
import com.artworkspace.themovie.R
import com.bumptech.glide.Glide

/**
 * Extension function to set image view from an url using Glide library
 *
 * @param context Context
 * @param url Image url
 */
fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.image_load_placeholder)
        .error(R.drawable.image_load_error)
        .into(this)
}

/**
 * Parse the rating data and determine which resource to display
 *
 * @param rating Film rating
 */
fun ImageView.parseMovieRating(rating: Int) {
    when (rating) {
        0 -> this.setImageResource(R.drawable.ic_rating_0)
        1 -> this.setImageResource(R.drawable.ic_rating_1)
        2 -> this.setImageResource(R.drawable.ic_rating_2)
        3 -> this.setImageResource(R.drawable.ic_rating_3)
        4 -> this.setImageResource(R.drawable.ic_rating_4)
        5 -> this.setImageResource(R.drawable.ic_rating_5)
        else -> throw IllegalArgumentException("Invalid rating argument")
    }
}

/**
 * Set image url complete endpoint, the image size is full/original
 *
 * @param path Image path
 * @return String
 */
fun getImageOriginalUrl(path: String?): String = "$API_IMG_ORIGINAL_ENDPOINT$path"


/**
 * Set image url complete endpoint, the image size is w500
 *
 * @param path Image path
 * @return String
 */
fun getImageUrl(path: String?): String = "$API_IMG_ENDPOINT$path"


/**
 * Animate visibility by transforming alpha value of a view
 *
 * @param isVisible View visibility
 * @param duration Animation duration, default 400
 */
fun View.animateAlpha(isVisible: Boolean, duration: Long = 400) {
    ObjectAnimator
        .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
        .setDuration(duration)
        .start()
}