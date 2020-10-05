package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import com.radutopor.dogsshouldvote.ui.breeddetails.models.SubBreedsViewState
import com.radutopor.dogsshouldvote.ui.breeds.mappers.BreedViewStateMapper
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import javax.inject.Inject

class SubBreedsViewStateMapper @Inject constructor(
    private val breedMapper: BreedViewStateMapper,
    private val resourceRepo: ResourceRepository,
) {

    fun map(subBreeds: List<Breed>, onClick: () -> Unit, onSubBreedClick: (String) -> Unit) =
        SubBreedsViewState(
            visibility = subBreeds.isNotEmpty(),
            symbol = resourceRepo.getString(R.string.arrow_down),
            onClick = onClick,
            subBreeds = subBreeds.map { breedMapper.map(it) { onSubBreedClick(it.id) } }
        )
}