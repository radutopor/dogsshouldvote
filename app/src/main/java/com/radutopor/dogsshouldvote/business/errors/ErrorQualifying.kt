package com.radutopor.dogsshouldvote.business.errors

import com.radutopor.dogsshouldvote.business.errors.mappers.ApiErrorMapper
import com.radutopor.dogsshouldvote.business.errors.models.ApiError
import com.radutopor.dogsshouldvote.webapi.getErrorApiResp
import retrofit2.HttpException

fun HttpException.getApiError(): ApiError? =
    getErrorApiResp()?.let { ApiErrorMapper().map(it) }