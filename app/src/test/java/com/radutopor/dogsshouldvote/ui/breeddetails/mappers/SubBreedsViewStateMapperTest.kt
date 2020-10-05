package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import com.radutopor.dogsshouldvote.ui.breeds.mappers.BreedViewStateMapper
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SubBreedsViewStateMapperTest {

    private lateinit var mapper: SubBreedsViewStateMapper
    private val breedViewStateMapper = mock<BreedViewStateMapper>()
    private val resourceRepo = mock<ResourceRepository>()
    private val arrowDown = "▼"
    private val subBreeds = (1..10).map { Breed(it.toString(), "") }

    @Before
    fun setUp() {
        whenever(resourceRepo.getString(R.string.arrow_down)).thenReturn(arrowDown)
        mapper = SubBreedsViewStateMapper(breedViewStateMapper, resourceRepo)
    }

    @Test
    fun `maps to SubBreedsViewState using resourceRepo`() {
        val onSubBreedsClick = {}

        val subBreedsViewState = mapper.map(emptyList(), onSubBreedsClick, {})

        subBreedsViewState.run {
            assertThat(symbol).isEqualTo(arrowDown)
            assertEquals(onClick, onSubBreedsClick)
        }
    }

    @Test
    fun `if list of sub-breeds is empty, visibility is false`() {
        val subBreedsViewState = mapper.map(emptyList(), {}, {})

        assertThat(subBreedsViewState.visibility).isFalse
    }

    @Test
    fun `if list of sub-breeds is not empty, visibility is true`() {
        val subBreedsViewState = mapper.map(listOf(mock()), {}, {})

        assertThat(subBreedsViewState.visibility).isTrue
    }

    @Test
    fun `maps lists of Breeds with given BreedViewStateMapper`() {
        val subBreedViewStates = mapper.map(subBreeds, {}, {})

        assertThat(subBreedViewStates.subBreeds).hasSameSizeAs(subBreeds)
        verify(breedViewStateMapper, times(subBreeds.size)).map(any(), any())
    }

    @Test
    fun `generates correct onClicks for sub-breeds`() {
        var onSubBreedClickSideEffect = ""
        val onSubBreedClick = { it: String -> onSubBreedClickSideEffect = it }

        mapper.map(subBreeds, {}, onSubBreedClick)

        val onClicks = argumentCaptor<() -> Unit>()
        verify(breedViewStateMapper, atLeastOnce()).map(any(), onClicks.capture())
        subBreeds.zip(onClicks.allValues).forEach { (breed, onClick) ->
            onClick()
            assertThat(onSubBreedClickSideEffect).isEqualTo(breed.id)
        }
    }
}