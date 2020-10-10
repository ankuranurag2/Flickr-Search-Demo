package dev.ananurag2.flickr.utils

import android.view.View
import dev.ananurag2.flickr.data.db.PhotoData

/**
 * created by ankur on 3/2/20
 */

/**
 * Image RecyclerView Adapter event listener
 */
public interface RecyclerViewEventListener {
    fun onPhotoSelect(data: PhotoData?, view: View)
    fun onBottomReached()
}