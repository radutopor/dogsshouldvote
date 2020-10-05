package com.radutopor.dogsshouldvote.ui.breeddetails.models

import com.radutopor.dogsshouldvote.ui.breeds.models.BreedViewState

data class SubBreedsViewState(
    val visibility: Boolean = false,
    val symbol: String = "",
    val rowsVisibility: Boolean = false,
    val subBreeds: List<BreedViewState> = emptyList(),
    val onClick: () -> Unit = {},
)