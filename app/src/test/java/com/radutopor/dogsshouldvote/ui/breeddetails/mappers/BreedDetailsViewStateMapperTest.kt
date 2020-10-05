package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.business.breeddetails.models.BreedDetails
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BreedDetailsViewStateMapperTest {

    private lateinit var mapper: BreedDetailsViewStateMapper
    private val subBreedsMapper = mock<SubBreedsViewStateMapper>()
    private val imageMapper = mock<ImageViewStateMapper>()

    private val breedDetails = BreedDetails(
        name = "breed",
        subBreeds = emptyList(),
        images = listOf("", "")
    )

    @Before
    fun setUp() {
        whenever(subBreedsMapper.map(any(), any(), any())).thenReturn(mock())
        mapper = BreedDetailsViewStateMapper(subBreedsMapper, imageMapper)
    }

    @Test
    fun `maps BreedDetails to BreedDetailsViewState`() {
        val breedDetailsViewState = mapper.map(breedDetails, {}, {})

        assertThat(breedDetailsViewState.name).isEqualTo(breedDetails.name)
    }

    @Test
    fun `maps sub-breeds using given subBreedsMapper`() {
        val onSubBreedsClick = {}
        val onSubBreedClick: (String) -> Unit = {}
        mapper.map(breedDetails, onSubBreedsClick, onSubBreedClick)

        verify(subBreedsMapper).map(breedDetails.subBreeds, onSubBreedsClick, onSubBreedClick)
    }

    @Test
    fun `maps images using given imageMapper`() {
        mapper.map(breedDetails, {}, { })

        verify(imageMapper, times(breedDetails.images.size)).map(any())
    }
}