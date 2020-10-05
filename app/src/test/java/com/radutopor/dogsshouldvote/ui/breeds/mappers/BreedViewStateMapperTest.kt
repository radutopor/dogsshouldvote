package com.radutopor.dogsshouldvote.ui.breeds.mappers

import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BreedViewStateMapperTest {

    private lateinit var mapper: BreedViewStateMapper

    @Before
    fun setUp() {
        mapper = BreedViewStateMapper()
    }

    @Test
    fun `maps Breed to BreedViewState`() {
        val breed = Breed(
            id = "id",
            name = "name"
        )
        val onBreedClick = {}
        val breedViewState = mapper.map(breed, onBreedClick)

        breedViewState.run {
            assertThat(name).isEqualTo(breed.name)
            assertEquals(onClick, onBreedClick)
        }
    }
}