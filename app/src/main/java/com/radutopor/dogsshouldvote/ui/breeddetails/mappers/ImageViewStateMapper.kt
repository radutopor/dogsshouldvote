package com.radutopor.dogsshouldvote.ui.breeddetails.mappers

import com.radutopor.dogsshouldvote.ui.breeddetails.models.ImageViewState
import javax.inject.Inject

class ImageViewStateMapper @Inject constructor() {

    fun map(imageUrl: String) = ImageViewState(
        imageUrl = imageUrl
    )
}