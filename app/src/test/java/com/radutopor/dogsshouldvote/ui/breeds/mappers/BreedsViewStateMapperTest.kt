package com.radutopor.dogsshouldvote.ui.breeds.mappers

import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BreedsViewStateMapperTest {

    private lateinit var mapper: BreedsViewStateMapper
    private val breedViewStateMapper = mock<BreedViewStateMapper>()
    private val breeds = (1..10).map { Breed(it.toString(), "") }

    @Before
    fun setUp() {
        mapper = BreedsViewStateMapper(breedViewStateMapper)
    }

    @Test
    fun `maps lists of Breeds with given BreedViewStateMapper`() {
        val breedViewStates = mapper.map(breeds) { }

        assertThat(breedViewStates).hasSameSizeAs(breeds)
        verify(breedViewStateMapper, times(breeds.size)).map(any(), any())
    }

    @Test
    fun `generates correct onClicks for breeds`() {
        var onBreedClickSideEffect = ""
        val onBreedClick = { it: String -> onBreedClickSideEffect = it }

        mapper.map(breeds, onBreedClick)

        val onClicks = argumentCaptor<() -> Unit>()
        verify(breedViewStateMapper, atLeastOnce()).map(any(), onClicks.capture())
        breeds.zip(onClicks.allValues).forEach { (breed, onClick) ->
            onClick()
            assertThat(onBreedClickSideEffect).isEqualTo(breed.id)
        }
    }
}