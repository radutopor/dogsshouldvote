package com.radutopor.dogsshouldvote.business.partbreeddetails.models

import com.radutopor.dogsshouldvote.business.breeds.models.Breed

data class PartBreedDetails(
    val name: String,
    val subBreeds: List<Breed>,
)