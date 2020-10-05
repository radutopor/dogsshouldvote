package com.radutopor.dogsshouldvote.ui.breeddetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.business.breeddetails.BreedDetailsUseCase
import com.radutopor.dogsshouldvote.business.breeddetails.models.BreedDetails
import com.radutopor.dogsshouldvote.ui.breeddetails.mappers.BreedDetailsViewStateMapper
import com.radutopor.dogsshouldvote.ui.breeddetails.models.BreedDetailsViewState
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.getUIMessage
import com.radutopor.dogsshouldvote.willAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BreedDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val tests = TestCoroutineDispatcher()

    private lateinit var viewModel: BreedDetailsViewModel
    private val useCase = mock<BreedDetailsUseCase>()
    private val mapper = mock<BreedDetailsViewStateMapper>()
    private val repo = mock<ResourceRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(tests)
        runBlocking { whenever(useCase.getBreedDetails(any())).doAnswer { mock() } }
        whenever(mapper.map(any(), any(), any())).thenReturn(mock())
        whenever(repo.getString(any())).thenReturn("")
        viewModel = BreedDetailsViewModel(useCase, mapper, repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        tests.cleanupTestCoroutines()
    }

    @Test
    fun `on init, sets loading state`() = tests.runBlockingTest {
        whenever(useCase.getBreedDetails(any())).willAnswer { delay(1); mock() }

        viewModel.init("")

        assertThat(viewModel.state.loading).isTrue
    }

    @Test
    fun `on init, gets breed details list`() = tests.runBlockingTest {
        val breedId = "breedId"

        viewModel.init(breedId)

        verify(useCase).getBreedDetails(breedId)
    }

    @Test
    fun `on init, gets breed details and uses mapper`() = tests.runBlockingTest {
        val breedDetails = mock<BreedDetails>()
        whenever(useCase.getBreedDetails(any())).doReturn(breedDetails)

        viewModel.init("")

        verify(mapper).map(eq(breedDetails), any(), any())
    }

    @Test
    fun `on init, gets breeds list, uses mapper and sets loaded state`() = tests.runBlockingTest {
        val breedDetailsVSs = mock<BreedDetailsViewState>()
        whenever(breedDetailsVSs.copy()).thenReturn(breedDetailsVSs)
        whenever(mapper.map(any(), any(), any())).doReturn(breedDetailsVSs)

        viewModel.init("")

        viewModel.state.run {
            assertThat(this).isEqualTo(breedDetailsVSs)
            assertThat(loading).isFalse
        }
    }

    @Test
    fun `on init, if get breeds error, sets error state with UI message`() = tests.runBlockingTest {
        val reqError = mock<RuntimeException>()
        val uiMessage = "UI message for repo"
        whenever(reqError.getUIMessage(repo)).thenReturn(uiMessage)
        whenever(useCase.getBreedDetails(any())).doThrow(reqError)

        viewModel.init("")

        viewModel.state.run {
            assertThat(loading).isFalse
            assertThat(error.visibility).isTrue
            assertThat(error.text).isEqualTo(uiMessage)
        }
    }

    @Test
    fun `on error, if retry, unsets error, sets loading and gets breeds list`() =
        tests.runBlockingTest {
            whenever(useCase.getBreedDetails(any()))
                .doThrow(RuntimeException())
                .willAnswer { delay(1); mock() }
            viewModel.init("")

            viewModel.state.error.onRetryClick()

            viewModel.state.run {
                assertThat(error.visibility).isFalse
                assertThat(loading).isTrue
            }
            verify(useCase, times(2)).getBreedDetails(any())
        }

    @Test
    fun `on sub-breeds click, expands sub-breeds view`() = tests.runBlockingTest {
        val arrowUp = "▲"
        whenever(repo.getString(R.string.arrow_up)).thenReturn(arrowUp)
        viewModel.init("")
        val subBreedsClickCapt = argumentCaptor<() -> Unit>()
        verify(mapper).map(any(), subBreedsClickCapt.capture(), any())

        subBreedsClickCapt.firstValue()

        viewModel.state.subBreeds.run {
            assertThat(rowsVisibility).isTrue
            assertThat(symbol).isEqualTo(arrowUp)
        }
    }

    @Test
    fun `on sub-breeds click again, collapses sub-breeds view`() = tests.runBlockingTest {
        val arrowDown = "▼"
        whenever(repo.getString(R.string.arrow_down)).thenReturn(arrowDown)
        viewModel.init("")
        val subBreedsClickCapt = argumentCaptor<() -> Unit>()
        verify(mapper).map(any(), subBreedsClickCapt.capture(), any())

        subBreedsClickCapt.firstValue()
        subBreedsClickCapt.firstValue()

        viewModel.state.subBreeds.run {
            assertThat(rowsVisibility).isFalse
            assertThat(symbol).isEqualTo(arrowDown)
        }
    }

    @Test
    fun `on sub-breed click, sets showSubBreedDetailsEvent`() = tests.runBlockingTest {
        viewModel.init("")
        val subBreedClickCapt = argumentCaptor<(String) -> Unit>()
        verify(mapper).map(any(), any(), subBreedClickCapt.capture())
        val subBreedId = "breed"

        subBreedClickCapt.firstValue(subBreedId)

        assertThat(viewModel.state.showSubBreedDetailsEvent).isEqualTo(ViewEvent(subBreedId))
    }
}