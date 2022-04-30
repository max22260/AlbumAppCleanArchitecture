package com.nagy.assessment.search.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nagy.assessment.R
import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.databinding.SearchFragmentBinding
import com.nagy.assessment.search.presentation.adapters.PhotoClickListener
import com.nagy.assessment.search.presentation.adapters.PhotosAdapter
import com.nagy.assessment.search.presentation.model.UiPhoto
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 3
        private const val EMPTY_STRING = ""
    }
    private val binding get() = _binding!!
    private var _binding: SearchFragmentBinding? = null
    private val args by navArgs<SearchFragmentArgs>()



    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        prepareForSearch()
    }

    private fun prepareForSearch() {

        setupSearchViewListener()
        viewModel.onEvent(SearchEvent.LoadAlbumPhotos(args.albumId))
        viewModel.onEvent(SearchEvent.QueryInput(EMPTY_STRING , args.albumId))
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }



    private fun createAdapter(): PhotosAdapter {
        return PhotosAdapter().apply {
            setOnPhotoClickListener(object : PhotoClickListener {
                override fun onClick(uiPhoto: UiPhoto) {
                    val action = SearchFragmentDirections.actionSearchToPhotoViewerFragment(uiPhoto.fullSize)
                    findNavController().navigate(action)
                }
            })
        }
    }

    private fun setupRecyclerView(searchAdapter: PhotosAdapter) {
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
            setHasFixedSize(true)
        }
    }

    private fun observeViewStateUpdates(searchAdapter: PhotosAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, searchAdapter)
        }
    }

    private fun updateScreenState(newState: SearchViewState, searchAdapter: PhotosAdapter) {

        val (
            loading,
            noSearchQuery,
            searchResults,
            failure,
        ) = newState

        binding.searchProgressBar.isVisible = loading
        searchAdapter.submitList(searchResults)
        handleFailures(failure)

    }

    private fun setupSearchViewListener() {
        val searchView = binding.searchWidget.search
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onEvent(SearchEvent.QueryInput(query.orEmpty() , args.albumId))
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onEvent(SearchEvent.QueryInput(newText.orEmpty(),args.albumId))
                    return true
                }
            }
        )
    }


    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}