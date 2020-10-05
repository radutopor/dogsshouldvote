package com.radutopor.dogsshouldvote.ui.breeddetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.business.breeddetails.BreedDetailsUseCase
import com.radutopor.dogsshouldvote.ui.breeddetails.mappers.BreedDetailsViewStateMapper
import com.radutopor.dogsshouldvote.ui.breeddetails.models.BreedDetailsViewState
import com.radutopor.dogsshouldvote.ui.common.ResourceRepository
import com.radutopor.dogsshouldvote.ui.common.StatefulViewModel
import com.radutopor.dogsshouldvote.ui.common.ViewEvent
import com.radutopor.dogsshouldvote.ui.common.getUIMessage
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BreedDetailsViewModel @ViewModelInject constructor(
    private val breedDetailsUseCase: BreedDetailsUseCase,
    private val breedDetailsMapper: BreedDetailsViewStateMapper,
    private val resourceRepo: ResourceRepository,
) : StatefulViewModel<BreedDetailsViewState>() {

    override val initialState = BreedDetailsViewState()

    fun init(breed: String) = // breed could be assisted injected as dependency
        getBreedDetails(breed)

    private fun getBreedDetails(breed: String): Job = viewModelScope.launch {
        state = state.copy(
            error = ErrorViewState(),   // reset error view state
            loading = true,
        )
        state = try {
            val breedDetails = breedDetailsUseCase.getBreedDetails(breed)
            breedDetailsMapper.map(breedDetails, ::onSubBreedsClick, ::onSubBreedClick)
        } catch (e: Exception) {
            state.copy(error = ErrorViewState(
                visibility = true,
                text = e.getUIMessage(resourceRepo),
                onRetryClick = { getBreedDetails(breed) }
            ))
        }.copy(loading = false)
    }

    private fun onSubBreedsClick() {
        state = state.copy(subBreeds = state.subBreeds.run {
            copy(
                rowsVisibility = !rowsVisibility,
                symbol = resourceRepo.getString(if (rowsVisibility) R.string.arrow_down else R.string.arrow_up)
            )
        })
    }

    private fun onSubBreedClick(id: String) {
        state = state.copy(showSubBreedDetailsEvent = ViewEvent(id))
    }
}
