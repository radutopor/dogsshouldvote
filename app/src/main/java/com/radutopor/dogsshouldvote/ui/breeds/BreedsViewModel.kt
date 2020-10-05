package com.radutopor.dogsshouldvote.ui.breeds

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.radutopor.dogsshouldvote.business.breeds.BreedsUseCase
import com.radutopor.dogsshouldvote.ui.breeds.mappers.BreedsViewStateMapper
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedsViewState
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import com.radutopor.dogsshouldvote.ui.common.StatefulViewModel
import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.getUIMessage
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BreedsViewModel @ViewModelInject constructor(
    private val breedsUseCase: BreedsUseCase,
    private val breedsMapper: BreedsViewStateMapper,
    private val resourceRepo: ResourceRepository,
) : StatefulViewModel<BreedsViewState>() {

    override val initialState = BreedsViewState()

    init {
        getBreeds()
    }

    private fun getBreeds(): Job = viewModelScope.launch {
        state = state.copy(
            error = ErrorViewState(),   // reset error view state
            loading = true,
        )
        state = try {
            val breeds = breedsMapper.map(breedsUseCase.getBreeds(), ::onBreedClick)
            state.copy(breeds = breeds)
        } catch (e: Exception) {
            state.copy(error = ErrorViewState(
                visibility = true,
                text = e.getUIMessage(resourceRepo),
                onRetryClick = { getBreeds() }
            ))
        }.copy(loading = false)
    }

    private fun onBreedClick(id: String) {
        state = state.copy(showBreedDetailsEvent = ViewEvent(id))
    }
}
