package com.radutopor.dogsshouldvote.ui.breeds.mappers

import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import javax.inject.Inject

class BreedsViewStateMapper @Inject constructor(
    private val breedMapper: BreedViewStateMapper,
) {

    fun map(breeds: List<Breed>, onBreedClick: (String) -> Unit) =
        breeds.map { breedMapper.map(it) { onBreedClick(it.id) } }
}