package com.nagy.assessment.search.presentation

import com.nagy.assessment.profile.presentation.model.UiAlbum

sealed class SearchEvent {
    data class LoadAlbumPhotos(val albumId : Long ) : SearchEvent()
    data class QueryInput(val input: String , val albumId : Long  ) : SearchEvent()
}