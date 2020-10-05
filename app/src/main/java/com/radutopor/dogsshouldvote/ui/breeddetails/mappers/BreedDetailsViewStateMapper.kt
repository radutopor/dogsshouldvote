package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import com.radutopor.dogsshouldvote.business.breeddetails.models.BreedDetails
import com.radutopor.dogsshouldvote.ui.breeddetails.models.BreedDetailsViewState
import javax.inject.Inject

class BreedDetailsViewStateMapper @Inject constructor(
    private val subBreedsMapper: SubBreedsViewStateMapper,
    private val imageMapper: ImageViewStateMapper,
) {

    fun map(
        breedDetails: BreedDetails,
        onSubBreedsClick: () -> Unit,
        onSubBreedClick: (String) -> Unit,
    ) = breedDetails.run {
        BreedDetailsViewState(
            name = name,
            subBreeds = subBreedsMapper.map(subBreeds, onSubBreedsClick, onSubBreedClick),
            images = images.map { imageMapper.map(it) }
        )
    }
}