package com.radutopor.dogsshouldvote.business.partbreeddetails

import com.nhaarman.mockitokotlin2.mock
import com.radutopor.dogsshouldvote.business.partbreeddetails.models.PartBreedDetails
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class PartBreedDetailsRepositoryTest {

    private lateinit var repo: PartBreedDetailsRepository

    @Before
    fun setUp() {
        repo = PartBreedDetailsRepository()
    }

    @Test
    fun `retrieves previously stored PartBreedDetails for id`() {
        val id = "id"
        val partBreedDetailsInput = mock<PartBreedDetails>()
        repo.putPartBreedDetails(id, partBreedDetailsInput)

        val partBreedDetails = repo.getPartDetails(id)

        assertThat(partBreedDetails).isEqualTo(partBreedDetailsInput)
    }

    @Test(expected = NoSuchElementException::class)
    fun `if no PartBreedDetails previously stored for id, throws NoSuchElementException`() {
        repo.getPartDetails("id")
    }
}