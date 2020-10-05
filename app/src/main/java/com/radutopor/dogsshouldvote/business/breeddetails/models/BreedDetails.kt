package com.radutopor.dogsshouldvote.business.breeddetails.models

import com.radutopor.dogsshouldvote.business.breeds.models.Breed

data class BreedDetails(
    val name: String,
    val subBreeds: List<Breed>,
    val images: List<String>,
)