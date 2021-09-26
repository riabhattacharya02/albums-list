package com.home.albums.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.albums.databinding.AlbumListItemBinding
import com.home.core.data.Album

class AlbumsListAdapter(val albums: ArrayList<Album>) :
    RecyclerView.Adapter<AlbumsListAdapter.AlbumsViewHolder>() {

    fun updateAlbums(newAlbums: List<Album>) {
        albums.clear()
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val binding =
            AlbumListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    inner class AlbumsViewHolder(binding: AlbumListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val albumTitle = binding.albumTitle

        fun bind(album: Album) {
            albumTitle.text = album.title
        }
    }
}