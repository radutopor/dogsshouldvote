package com.radutopor.dogsshouldvote.business.breeds

import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import com.radutopor.dogsshouldvote.business.partbreeddetails.PartBreedDetailsRepository
import com.radutopor.dogsshouldvote.business.partbreeddetails.models.PartBreedDetails
import com.radutopor.dogsshouldvote.webapi.DogWebApi
import javax.inject.Inject

class BreedsUseCase @Inject constructor(
    private val webApi: DogWebApi,
    private val partDetailsRepo: PartBreedDetailsRepository,
) {

    suspend fun getBreeds() = webApi.getBreeds().message.entries.map { (breed, subBreeds) ->
        val breed = Breed(breed, breed.capitalize())
        partDetailsRepo.putPartBreedDetails(breed.id, PartBreedDetails(
            name = breed.name,
            subBreeds = subBreeds.map {
                val subBreed = Breed("${breed.id}/$it", "$it ${breed.name}".capitalize())
                partDetailsRepo.putPartBreedDetails(subBreed.id, PartBreedDetails(
                    name = subBreed.name,
                    subBreeds = emptyList())
                )
                subBreed
            }
        ))
        breed
    }
}