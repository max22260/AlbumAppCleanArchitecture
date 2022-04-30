package com.nagy.assessment.profile.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagy.assessment.databinding.RecyclerViewAlbumItemBinding
import com.nagy.assessment.profile.presentation.model.UiAlbum

class ProfileAdapter : ListAdapter<UiAlbum, ProfileAdapter.AlbumViewHolder>(ITEM_COMPARATOR) {

    private var albumClickListener: AlbumClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = RecyclerViewAlbumItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item: UiAlbum = getItem(position)

        holder.bind(item)
    }

    inner class AlbumViewHolder(
        private val binding: RecyclerViewAlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiAlbum) {
            binding.name.text = item.title

            binding.root.setOnClickListener {
                albumClickListener?.onClick(item.albumId)
            }
        }
    }

    fun setOnAlbumClickListener(albumClickListener: AlbumClickListener) {
        this.albumClickListener = albumClickListener
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiAlbum>() {
    override fun areItemsTheSame(oldItem: UiAlbum, newItem: UiAlbum): Boolean {
        return oldItem.albumId == newItem.albumId
    }

    override fun areContentsTheSame(oldItem: UiAlbum, newItem: UiAlbum): Boolean {
        return oldItem == newItem
    }
}


interface AlbumClickListener {
    fun onClick(animalId: Long)
}