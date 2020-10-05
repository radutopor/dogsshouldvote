package com.radutopor.dogsshouldvote.render.breeds

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.render.common.ScreenRender
import com.radutopor.dogsshouldvote.render.common.padding
import com.radutopor.dogsshouldvote.render.common.randomColour
import com.radutopor.dogsshouldvote.ui.breeds.BreedsViewModel
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedViewState
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedsViewState
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState

@Composable
fun BreedsRender() {
    val state by viewModel<BreedsViewModel>().run { stateObservable.observeAsState(initialState) }
    Breeds(state)
}

@Preview
@Composable
fun PreviewBreeds() = Breeds(
    BreedsViewState(
        loading = true,
        error = ErrorViewState(true, "Something wrong"),
        breeds = listOf(
            BreedViewState("Breed 1") {},
            BreedViewState("Breed 2") {},
            BreedViewState("Breed 3") {},
        )
    ),
)

@Composable
fun Breeds(breedsVS: BreedsViewState) = breedsVS.run {
    ScreenRender(
        loading = loading,
        error = error,
        title = stringResource(id = R.string.app_name))
    { BreedsList(breeds) }
}

@Composable
fun BreedsList(breeds: List<BreedViewState>) =
    LazyColumnFor(breeds) { breed ->
        Column(modifier = Modifier.clickable(onClick = breed.onClick)) {
            Text(
                text = breed.name,
                fontSize = 18.sp,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = padding(),
                        horizontal = dimensionResource(id = R.dimen.padding_wide)
                    )
            )
            Divider(
                color = remember { randomColour() },
                thickness = 2.dp,
            )
        }
    }