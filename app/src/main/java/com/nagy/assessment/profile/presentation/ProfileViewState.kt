package com.nagy.assessment.profile.presentation

import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.profile.presentation.model.UiAlbum

data class ProfileViewState(
    val loading: Boolean = true,
    val noUser : Boolean = false ,
    val noAlbums : Boolean = false ,
    val userName: String = "",
    val userAddress: String = "",
    val albums : List<UiAlbum> = emptyList(),
    val failure: Event<Throwable>? = null
)