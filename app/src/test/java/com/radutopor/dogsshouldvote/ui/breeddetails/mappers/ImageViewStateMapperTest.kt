package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ImageViewStateMapperTest {

    private lateinit var mapper: ImageViewStateMapper

    @Before
    fun setUp() {
        mapper = ImageViewStateMapper()
    }

    @Test
    fun `maps imageUrl to ImageViewState`() {
        val imageUrl = "iamgeUrl"

        val imageViewState = mapper.map(imageUrl)

        assertThat(imageViewState.imageUrl).isEqualTo(imageUrl)
    }
}