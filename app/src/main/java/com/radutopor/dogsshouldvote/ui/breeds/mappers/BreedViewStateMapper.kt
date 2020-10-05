package com.radutopor.dogsshouldvote.ui.breeds.mappers

import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedViewState
import javax.inject.Inject

class BreedViewStateMapper @Inject constructor() {

    fun map(breed: Breed, onClick: () -> Unit) = BreedViewState(
        name = breed.name,
        onClick = onClick
    )
}