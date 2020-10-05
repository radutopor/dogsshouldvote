package com.radutopor.dogsshouldvote.ui.breeds.models

data class BreedViewState(
    val name: String,
    val onClick: () -> Unit
)