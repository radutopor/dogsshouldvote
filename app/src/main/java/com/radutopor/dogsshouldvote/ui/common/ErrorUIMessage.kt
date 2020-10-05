package com.radutopor.dogsshouldvote.ui.common

import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.business.errors.getApiError
import retrofit2.HttpException

fun Exception.getUIMessage(resourceRepo: ResourceRepository) =
    (this as? HttpException)?.getApiError()?.message ?: resourceRepo.getString(R.string.oops)