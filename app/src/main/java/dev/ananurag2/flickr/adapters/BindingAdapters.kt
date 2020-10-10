package dev.ananurag2.flickr.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * created by ankur on 31/1/20
 */

@BindingAdapter("loadImage")
fun bindImageWithUrl(view: ImageView, imgUrl: String?) {
    imgUrl ?: return
    Glide.with(view.context)
        .load(imgUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}