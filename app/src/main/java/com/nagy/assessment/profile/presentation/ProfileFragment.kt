package com.nagy.assessment.profile.presentation

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nagy.assessment.R
import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.databinding.ProfileFragmentBinding
import com.nagy.assessment.profile.presentation.adapters.AlbumClickListener
import com.nagy.assessment.profile.presentation.adapters.ProfileAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var navController: NavController


    private val binding get() = _binding!!

    private var _binding: ProfileFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupUI()
        requestInitialState()
    }

    private fun requestInitialState() {

       viewModel.onEvent(ProfileEvent.RequestInitialRandomUser)
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun observeViewStateUpdates(adapter: ProfileAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)

        }
    }

    private fun updateScreenState(state: ProfileViewState, adapter: ProfileAdapter) {
        binding.progressBar.isVisible = state.loading
        binding.userName.text = state.userName
        binding.addressName.text = state.userAddress
        adapter.submitList(state.albums)
        handleFailures(state.failure)
    }


    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!! }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView(profileAdapter: ProfileAdapter) {
        binding.albumsRecyclerView.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
    private fun createAdapter(): ProfileAdapter {
        return ProfileAdapter().apply {
            setOnAlbumClickListener(object : AlbumClickListener {
                override fun onClick(animalId: Long) {

                    val action = ProfileFragmentDirections.actionProfileToSearch(animalId)
                    findNavController().navigate(action)
                }
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}