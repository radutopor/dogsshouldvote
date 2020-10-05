package com.radutopor.dogsshouldvote.business.breeds

import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.business.partbreeddetails.PartBreedDetailsRepository
import com.radutopor.dogsshouldvote.business.partbreeddetails.models.PartBreedDetails
import com.radutopor.dogsshouldvote.webapi.DogWebApi
import com.radutopor.dogsshouldvote.webapi.models.BreedsApiResp
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BreedsUseCaseTest {

    private lateinit var useCase: BreedsUseCase
    private val webApi = mock<DogWebApi>()
    private val repo = mock<PartBreedDetailsRepository>()

    private val breedsApiResp = BreedsApiResp(
        status = "success",
        message = mapOf(
            "hound" to listOf(
                "afghan",
                "basset"
            ),
            "poodle" to listOf(
                "miniature",
                "standard"
            )
        ),
        code = null
    )

    @Before
    fun setUp() {
        runBlocking { whenever(webApi.getBreeds()).thenReturn(breedsApiResp) }
        useCase = BreedsUseCase(webApi, repo)
    }

    @Test
    fun `gets breeds from given WebApi`() = runBlockingTest {
        useCase.getBreeds()

        verify(webApi).getBreeds()
    }

    @Test
    fun `returns list of mapped Breeds`() = runBlockingTest {
        val breeds = useCase.getBreeds()

        breedsApiResp.message.keys.forEachIndexed { i, key ->
            breeds[i].run {
                assertThat(id).isEqualTo(key)
                assertThat(name).isEqualTo(key.capitalize())
            }
        }
    }

    @Test
    fun `puts PartBreedDetails in repo for each breed and sub-breed`() = runBlockingTest {
        useCase.getBreeds()

        val idCapt = argumentCaptor<String>()
        val partDetailsCapt = argumentCaptor<PartBreedDetails>()
        verify(repo, times(6)).putPartBreedDetails(idCapt.capture(), partDetailsCapt.capture())

        assertThat(idCapt.allValues[0]).isEqualTo("hound/afghan")
        var partDetails = partDetailsCapt.allValues[0]
        assertThat(partDetails.name).isEqualTo("Afghan Hound")
        assertThat(partDetails.subBreeds).isEmpty()

        assertThat(idCapt.allValues[1]).isEqualTo("hound/basset")
        partDetails = partDetailsCapt.allValues[1]
        assertThat(partDetails.name).isEqualTo("Basset Hound")
        assertThat(partDetails.subBreeds).isEmpty()

        assertThat(idCapt.allValues[2]).isEqualTo("hound")
        partDetails = partDetailsCapt.allValues[2]
        assertThat(partDetails.name).isEqualTo("Hound")
        assertThat(partDetails.subBreeds).hasSize(2)
        assertThat(partDetails.subBreeds[0].id).isEqualTo("hound/afghan")
        assertThat(partDetails.subBreeds[0].name).isEqualTo("Afghan Hound")
        assertThat(partDetails.subBreeds[1].id).isEqualTo("hound/basset")
        assertThat(partDetails.subBreeds[1].name).isEqualTo("Basset Hound")

        assertThat(idCapt.allValues[3]).isEqualTo("poodle/miniature")
        partDetails = partDetailsCapt.allValues[3]
        assertThat(partDetails.name).isEqualTo("Miniature Poodle")
        assertThat(partDetails.subBreeds).isEmpty()

        assertThat(idCapt.allValues[4]).isEqualTo("poodle/standard")
        partDetails = partDetailsCapt.allValues[4]
        assertThat(partDetails.name).isEqualTo("Standard Poodle")
        assertThat(partDetails.subBreeds).isEmpty()

        assertThat(idCapt.allValues[5]).isEqualTo("poodle")
        partDetails = partDetailsCapt.allValues[5]
        assertThat(partDetails.name).isEqualTo("Poodle")
        assertThat(partDetails.subBreeds).hasSize(2)
        assertThat(partDetails.subBreeds[0].id).isEqualTo("poodle/miniature")
        assertThat(partDetails.subBreeds[0].name).isEqualTo("Miniature Poodle")
        assertThat(partDetails.subBreeds[1].id).isEqualTo("poodle/standard")
        assertThat(partDetails.subBreeds[1].name).isEqualTo("Standard Poodle")
    }
}