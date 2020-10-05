package com.radutopor.dogsshouldvote.business.breeddetails

import com.radutopor.dogsshouldvote.business.partbreeddetails.PartBreedDetailsRepository
import com.radutopor.dogsshouldvote.business.breeddetails.models.BreedDetails
import com.radutopor.dogsshouldvote.webapi.DogWebApi
import javax.inject.Inject

class BreedDetailsUseCase @Inject constructor(
    private val webApi: DogWebApi,
    private val partDetailsRepo: PartBreedDetailsRepository,
) {

    suspend fun getBreedDetails(id: String): BreedDetails {
        val partialDetails = partDetailsRepo.getPartDetails(id)
        val imagesApiResp = if (id.contains("/")) id.split("/")
            .let { webApi.getSubBreedImages(it[0], it[1]) } else webApi.getBreedImages(id)
        return BreedDetails(
            name = partialDetails.name,
            subBreeds = partialDetails.subBreeds,
            images = imagesApiResp.message
        )
    }
}