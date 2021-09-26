package com.home.albums.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.albums.databinding.FragmentAlbumBinding
import com.home.albums.framework.viewmodel.AlbumListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumFragment : Fragment() {

    private val albumsListAdapter = AlbumsListAdapter(arrayListOf())
    private val albumListViewModel: AlbumListViewModel by viewModel()
    private lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.albumsListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumsListAdapter
        }

        initObservers()
    }

    override fun onResume() {
        super.onResume()
        albumListViewModel.getAlbums()
    }

    private fun initObservers() {
        albumListViewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            binding.progressBar.visibility = View.GONE
            binding.albumsListView.visibility = View.VISIBLE
            albumsListAdapter.updateAlbums(albums.sortedBy { it.title })
        })
    }
}