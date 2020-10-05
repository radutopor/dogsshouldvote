package com.radutopor.dogsshouldvote.ui.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.radutopor.dogsshouldvote.business.breeds.BreedsUseCase
import com.radutopor.dogsshouldvote.business.breeds.models.Breed
import com.radutopor.dogsshouldvote.ui.breeds.mappers.BreedsViewStateMapper
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedViewState
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.getUIMessage
import com.radutopor.dogsshouldvote.willAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BreedsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val tests = TestCoroutineDispatcher()

    private lateinit var viewModel: BreedsViewModel
    private val useCase = mock<BreedsUseCase>()
    private val mapper = mock<BreedsViewStateMapper>()
    private val repo = mock<ResourceRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(tests)
        whenever(repo.getString(any())).thenReturn("")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        tests.cleanupTestCoroutines()
    }

    private fun initViewModel() {
        viewModel = BreedsViewModel(useCase, mapper, repo)
    }

    @Test
    fun `on init, sets loading state`() = tests.runBlockingTest {
        whenever(useCase.getBreeds()).willAnswer { delay(1); mock() }

        initViewModel()

        assertThat(viewModel.state.loading).isTrue
    }

    @Test
    fun `on init, gets breeds list`() = tests.runBlockingTest {
        initViewModel()

        verify(useCase).getBreeds()
    }

    @Test
    fun `on init, gets breeds list and uses mapper`() = tests.runBlockingTest {
        val breeds = mock<List<Breed>>()
        whenever(useCase.getBreeds()).doAnswer { breeds }

        initViewModel()

        verify(mapper).map(eq(breeds), any())
    }

    @Test
    fun `on init, gets breeds list, uses mapper and sets loaded state`() = tests.runBlockingTest {
        whenever(useCase.getBreeds()).doAnswer { mock() }
        val breedVSs = mock<List<BreedViewState>>()
        whenever(mapper.map(any(), any())).doAnswer { breedVSs }

        initViewModel()

        viewModel.state.run {
            assertThat(loading).isFalse
            assertThat(breeds).isEqualTo(breedVSs)
        }
    }

    @Test
    fun `on init, if get breeds error, sets error state with UI message`() = tests.runBlockingTest {
        val reqError = mock<RuntimeException>()
        val uiMessage = "UI message for repo"
        whenever(reqError.getUIMessage(repo)).thenReturn(uiMessage)
        whenever(useCase.getBreeds()).doThrow(reqError)

        initViewModel()

        viewModel.state.run {
            assertThat(loading).isFalse
            assertThat(error.visibility).isTrue
            assertThat(error.text).isEqualTo(uiMessage)
        }
    }

    @Test
    fun `on error, if retry, unsets error, sets loading and gets breeds list`() =
        tests.runBlockingTest {
            whenever(useCase.getBreeds())
                .doThrow(RuntimeException())
                .willAnswer { delay(1); mock() }
            initViewModel()

            viewModel.state.error.onRetryClick()

            viewModel.state.run {
                assertThat(error.visibility).isFalse
                assertThat(loading).isTrue
            }
            verify(useCase, times(2)).getBreeds()
        }

    @Test
    fun `on breed click, sets showBreedDetailsEvent`() = tests.runBlockingTest {
        whenever(useCase.getBreeds()).doAnswer { mock() }
        initViewModel()
        val breedClickCapt = argumentCaptor<(String) -> Unit>()
        verify(mapper).map(any(), breedClickCapt.capture())
        val breedId = "breed"

        breedClickCapt.firstValue(breedId)

        assertThat(viewModel.state.showBreedDetailsEvent).isEqualTo(ViewEvent(breedId))
    }
}