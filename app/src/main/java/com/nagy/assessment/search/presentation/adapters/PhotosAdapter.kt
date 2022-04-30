package com.nagy.assessment.search.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagy.assessment.common.utils.setImage
import com.nagy.assessment.databinding.RecyclerViewPhotoItemBinding
import com.nagy.assessment.search.presentation.model.UiPhoto


class PhotosAdapter: ListAdapter<UiPhoto, PhotosAdapter.PhotoViewHolder>(ITEM_COMPARATOR) {


    private var photoClickListener: PhotoClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = RecyclerViewPhotoItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item: UiPhoto = getItem(position)

        holder.bind(item)
    }

    inner class PhotoViewHolder(
        private val binding: RecyclerViewPhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiPhoto) {
            binding.name.text = item.title
            binding.photo.setImage(item.fullSize )

            binding.root.setOnClickListener {
                photoClickListener?.onClick(item)
            }
        }
    }

    fun setOnPhotoClickListener(photoClickListener: PhotoClickListener) {
        this.photoClickListener = photoClickListener
    }
}



private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiPhoto>() {
    override fun areItemsTheSame(oldItem: UiPhoto, newItem: UiPhoto): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: UiPhoto, newItem: UiPhoto): Boolean {
        return oldItem == newItem
    }
}


interface PhotoClickListener {
    fun onClick(uiPhoto: UiPhoto)
}