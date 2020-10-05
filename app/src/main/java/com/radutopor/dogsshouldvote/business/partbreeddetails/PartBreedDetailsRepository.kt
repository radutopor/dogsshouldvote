package com.radutopor.dogsshouldvote.business.partbreeddetails

import com.radutopor.dogsshouldvote.business.partbreeddetails.models.PartBreedDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartBreedDetailsRepository @Inject constructor() {
    private val store = HashMap<String, PartBreedDetails>()

    fun putPartBreedDetails(breedId: String, partDetails: PartBreedDetails) =
        store.put(breedId, partDetails)

    fun getPartDetails(breedId: String) = store[breedId]
        ?: throw NoSuchElementException("No partial details found for $breedId")
}