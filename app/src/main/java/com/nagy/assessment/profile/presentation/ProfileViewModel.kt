package com.nagy.assessment.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagy.assessment.common.domian.model.*
import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.common.utils.DispatchersProvider
import com.nagy.assessment.common.utils.createExceptionHandler
import com.nagy.assessment.profile.domain.usecases.*
import com.nagy.assessment.profile.presentation.model.mappers.UiAlbumMapper
import com.nagy.logging.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val requestUsers: RequestUsers,
    private val getUserWithAlbums: GetUserWithAlbums,
    private val requestUserAlbums: RequestUserAlbums,
    private val uiAlbumMapper: UiAlbumMapper,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {


    val state: LiveData<ProfileViewState> get() = _state
    private val _state = MutableLiveData<ProfileViewState>()

    init {
        _state.value = ProfileViewState()
        //onRequestNewUserWithAlbums()
    }

    private suspend fun getUsrUpdate(userId: Long): UserWithAlbum {
        return withContext(dispatchersProvider.io()) {
            getUserWithAlbums(userId)
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.RequestInitialRandomUser -> loadUserAlbums()
        }
    }

    private fun loadUserAlbums() {
        if (state.value!!.albums.isEmpty()) {
            onRequestNewUserWithAlbums()
        }
    }

    private fun onRequestNewUserWithAlbums() {
        val errorMessage = "Failed to fetch users"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {

            val user = onRequestUser()
            onRequestUserAlbums(user.id.toInt())
            val userWithAlbum = getUsrUpdate(user.id)

            _state.value = state.value!!.copy(
                loading = false,
                userName = userWithAlbum.user.name ,
                userAddress = userWithAlbum.user.formattedAddress(),
                albums =  userWithAlbum.Albums.map { uiAlbumMapper.mapToView(it) }
            )

        }

    }

    private suspend fun onRequestUser(): User {
        return withContext(dispatchersProvider.io()) {
            requestUsers()
        }
    }

    private suspend fun onRequestUserAlbums(userId: Int) {
        return withContext(dispatchersProvider.io()) {
            requestUserAlbums(userId)
        }
    }


    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoUsersException -> {
                _state.value = state.value!!.copy(
                    noUser = true,
                    failure = Event(failure)
                )
            }
            is NoAlbumsException -> {
                _state.value = state.value!!.copy(
                    noAlbums = true,
                    failure = Event(failure)
                )
            }
        }
    }
}