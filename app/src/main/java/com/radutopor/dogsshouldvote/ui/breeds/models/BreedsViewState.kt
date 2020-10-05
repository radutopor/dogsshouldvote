package com.radutopor.dogsshouldvote.ui.breeds.models

import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState

data class BreedsViewState(
    val loading: Boolean = false,
    val error: ErrorViewState = ErrorViewState(),
    val breeds: List<BreedViewState> = emptyList(),
    val showBreedDetailsEvent: ViewEvent<String> = ViewEvent(),     // w/ breed id
)