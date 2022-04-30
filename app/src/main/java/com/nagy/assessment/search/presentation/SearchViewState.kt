package com.nagy.assessment.search.presentation

import com.nagy.assessment.common.presentation.model.Event
import com.nagy.assessment.search.presentation.model.UiPhoto

data class SearchViewState(
    val loading: Boolean = true,
    val noSearchQuery: Boolean = true,
    val searchResults: List<UiPhoto> = emptyList(),
    val failure: Event<Throwable>? = null
)
