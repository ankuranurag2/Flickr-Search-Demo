package dev.ananurag2.flickr.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ananurag2.flickr.databinding.ListItemPhotoBinding
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.utils.RecyclerViewEventListener

/**
 * created by ankur on 31/1/20
 */
class ImageAdapter(private val mListener: RecyclerViewEventListener) :
    ListAdapter<PhotoData, RecyclerView.ViewHolder>(PhotoDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoViewHolder(ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == itemCount - 1)
            mListener.onBottomReached()
        (holder as PhotoViewHolder).bind(getItem(position))
    }

    class PhotoViewHolder(private val binding: ListItemPhotoBinding, private val listener: RecyclerViewEventListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.photoItemIv.setOnClickListener {
                listener.onPhotoSelect(binding.photo, it)
            }
        }

        fun bind(item: PhotoData) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }
    }
}

private class PhotoDiffCallBack : DiffUtil.ItemCallback<PhotoData>() {
    override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
        return oldItem == newItem
    }
}