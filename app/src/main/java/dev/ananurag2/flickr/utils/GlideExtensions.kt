package dev.ananurag2.flickr.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * created by ankur on 1/2/20
 */

/**
 * Extension method to provide Glide callback, so that postponedTransitions are resumed
 */
fun ImageView.loadImage(url: String, onLoadingDone: () -> Unit = {}) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            onLoadingDone()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?, model: Any?, target: Target<Drawable>?,
            dataSource: DataSource?, isFirstResource: Boolean
        ): Boolean {
            onLoadingDone()
            return false
        }

    }

    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .apply(RequestOptions().dontTransform())
        .listener(listener)
        .into(this)
}