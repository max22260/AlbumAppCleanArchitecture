package com.nagy.assessment.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagy.assessment.common.domian.model.NoPhotosException
import com.nagy.assessment.common.domian.model.Photo
import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.common.utils.createExceptionHandler
import com.nagy.assessment.search.domain.usecases.RequestAlbumPhotos
import com.nagy.assessment.search.domain.usecases.SearchPhotos
import com.nagy.assessment.search.presentation.model.mappers.UiPhotoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val requestAlbumPhotos: RequestAlbumPhotos,
    private val searchPhotos: SearchPhotos,
    private val uiPhotoMapper: UiPhotoMapper,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val state: LiveData<SearchViewState> get() = _state
    private val _state: MutableLiveData<SearchViewState> = MutableLiveData()
    private val querySubject = BehaviorSubject.create<String>()

    init {
        _state.value = SearchViewState()
    }


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.LoadAlbumPhotos -> loadOnlinePhoto(event.albumId.toInt())
            is SearchEvent.QueryInput -> updateQuery( event.input ,event.albumId)
        }
    }

    private fun updateQuery(name : String, albumId: Long) {
        querySubject.onNext(name)
        if (name.isNotEmpty()){
            _state.value = state.value!!.copy(
                loading = false ,
                noSearchQuery = false,
                searchResults = state.value!!.searchResults.filter { it.title.contains(name) }
            )
        }
        searchPhotos(querySubject ,albumId )
            .observeOn(AndroidSchedulers.mainThread()) .subscribe(
                { onSearchResults(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onSearchResults(photos: List<Photo>) {

        if (photos.isEmpty()) {
            onFailure(NoPhotosException("no result"))
        } else {
            _state.value = state.value!!.copy(
                loading = false ,
                noSearchQuery = false,
                searchResults = photos.map { uiPhotoMapper.mapToView(it) }
            )
        }

    }

    private fun loadOnlinePhoto(albumId : Int) {
        val exceptionHandler =
            createExceptionHandler(message = "Failed to load Album photos.")
        viewModelScope.launch(exceptionHandler) {
            requestAlbumPhotos(albumId)
        }

    }


    private fun onFailure(throwable: Throwable) {
        _state.value = if (throwable is NoPhotosException) {
            state.value!!.copy(loading = false, failure = Event(throwable))
        } else {
            state.value!!.copy(failure = Event(throwable))
        }

    }

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
        return viewModelScope.createExceptionHandler(message) {
            onFailure(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

