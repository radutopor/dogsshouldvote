package com.radutopor.dogsshouldvote.ui.breeddetails.models

import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState

data class BreedDetailsViewState(
    val loading: Boolean = false,
    val error: ErrorViewState = ErrorViewState(),
    val name: String = "",
    val subBreeds: SubBreedsViewState = SubBreedsViewState(),
    val images: List<ImageViewState> = emptyList(),
    val showSubBreedDetailsEvent: ViewEvent<String> = ViewEvent(),     // w/ breed id
)