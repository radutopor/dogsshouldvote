package com.radutopor.dogsshouldvote.render.breeddetails

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.render.common.ScreenRender
import com.radutopor.dogsshouldvote.render.common.padding
import com.radutopor.dogsshouldvote.render.common.randomColour
import com.radutopor.dogsshouldvote.ui.breeddetails.BreedDetailsViewModel
import com.radutopor.dogsshouldvote.ui.breeddetails.models.BreedDetailsViewState
import com.radutopor.dogsshouldvote.ui.breeddetails.models.ImageViewState
import com.radutopor.dogsshouldvote.ui.breeddetails.models.SubBreedsViewState
import com.radutopor.dogsshouldvote.ui.breeds.models.BreedViewState
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun BreedDetailsRender() {
    val state by viewModel<BreedDetailsViewModel>().run {
        stateObservable.observeAsState(initialState)
    }
    BreedDetails(state)
}

@Preview
@Composable
fun PreviewBreedDetails() = BreedDetails(
    BreedDetailsViewState(
        loading = true,
        error = ErrorViewState(true, "Something wrong"),
        name = "Breed name",
        subBreeds = SubBreedsViewState(
            visibility = true,
            symbol = "▲",
            rowsVisibility = true,
            subBreeds = listOf(
                BreedViewState("Sub-breed 1") {},
                BreedViewState("Sub-breed 2") {},
                BreedViewState("Sub-breed 3") {},
            )
        ),
        images = listOf(
            ImageViewState("https://images.dog.ceo/breeds/waterdog-spanish/20180723_185544.jpg"),
            ImageViewState("https://images.dog.ceo/breeds/pointer-germanlonghair/hans1.jpg"),
            ImageViewState("https://images.dog.ceo/breeds/schnauzer-giant/n02097130_3615.jpg")
        )
    ),
)

@Composable
fun BreedDetails(breedDetails: BreedDetailsViewState) = breedDetails.run {
    ScreenRender(
        loading = loading,
        error = error,
        title = name)
    { Content(breedDetails) }
}

@Composable
fun Content(breedDetails: BreedDetailsViewState) = breedDetails.run {
    Column {
        SubBreeds(subBreeds)
        ImageList(images)
    }
}

@Composable
fun SubBreeds(subBreedsViewState: SubBreedsViewState) = subBreedsViewState.run {
    if (visibility) {
        Column(modifier = Modifier.background(MaterialTheme.colors.secondary)) {
            Row(modifier = Modifier.fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(padding())
            ) {
                SubBreedsHeaderText(stringResource(id = R.string.sub_breeds), Modifier.weight(1f))
                SubBreedsHeaderText(symbol)
            }
            if (rowsVisibility) LazyColumnFor(subBreeds) { subBreed ->
                Column(modifier = Modifier.clickable(onClick = subBreed.onClick)) {
                    Divider(
                        color = MaterialTheme.colors.primary,
                        thickness = 1.dp,
                    )
                    Text(
                        text = subBreed.name,
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = padding(),
                                horizontal = dimensionResource(id = R.dimen.padding_wide)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun SubBreedsHeaderText(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    color = MaterialTheme.colors.onSecondary,
    style = MaterialTheme.typography.h5,
    modifier = modifier
)

@Composable
fun ImageList(images: List<ImageViewState>) =
    LazyColumnFor(images) { image ->
        CoilImage(
            data = image.imageUrl,
            loading = {
                Spacer(modifier = Modifier.fillMaxSize()
                    .background(remember { randomColour() }))
            },
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
                .preferredHeight(320.dp)
                .padding(top = padding())
        )
    }