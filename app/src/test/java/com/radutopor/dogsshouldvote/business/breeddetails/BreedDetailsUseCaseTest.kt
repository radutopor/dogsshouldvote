package com.radutopor.dogsshouldvote.business.breeddetails

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.radutopor.dogsshouldvote.business.partbreeddetails.PartBreedDetailsRepository
import com.radutopor.dogsshouldvote.business.partbreeddetails.models.PartBreedDetails
import com.radutopor.dogsshouldvote.webapi.DogWebApi
import com.radutopor.dogsshouldvote.webapi.models.ImagesApiResp
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BreedDetailsUseCaseTest {

    private lateinit var useCase: BreedDetailsUseCase
    private val webApi = mock<DogWebApi>()
    private val repo = mock<PartBreedDetailsRepository>()

    private val partBreedDetails = PartBreedDetails(
        name = "Breed",
        subBreeds = emptyList()
    )
    private val imagesApiResp = ImagesApiResp(
        status = "success",
        message = emptyList(),
        code = null
    )

    @Before
    fun setUp() {
        whenever(repo.getPartDetails(any())).thenReturn(partBreedDetails)
        runBlocking {
            whenever(webApi.getBreedImages(any())).thenReturn(imagesApiResp)
            whenever(webApi.getSubBreedImages(any(), any())).thenReturn(imagesApiResp)
        }
        useCase = BreedDetailsUseCase(webApi, repo)
    }

    @Test
    fun `gets PartBreedDetails from repo for given id`() = runBlockingTest {
        val id = "breed"

        useCase.getBreedDetails(id)

        verify(repo).getPartDetails(id)
    }

    @Test
    fun `if top level breed, gets breed images from webApi`() = runBlockingTest {
        val id = "breed"

        useCase.getBreedDetails(id)

        verify(webApi).getBreedImages(id)
    }

    @Test
    fun `if sub-breed, gets sub breed images from webApi`() = runBlockingTest {
        val breed = "breed"
        val subBreed = "subBreed"

        useCase.getBreedDetails("$breed/$subBreed")

        verify(webApi).getSubBreedImages(breed, subBreed)
    }

    @Test
    fun `maps retrieved data to BreedDetails`() = runBlockingTest {
        val breedDetails = useCase.getBreedDetails("breed")

        breedDetails.run {
            assertThat(name).isEqualTo(partBreedDetails.name)
            assertThat(subBreeds).isEqualTo(partBreedDetails.subBreeds)
            assertThat(images).isEqualTo(imagesApiResp.message)
        }
    }
}