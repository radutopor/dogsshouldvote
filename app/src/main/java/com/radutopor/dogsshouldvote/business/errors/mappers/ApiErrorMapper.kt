package com.radutopor.dogsshouldvote.business.errors.mappers

import com.radutopor.dogsshouldvote.business.errors.models.ApiError
import com.radutopor.dogsshouldvote.webapi.models.ErrorApiResp
import java.text.ParseException
import javax.inject.Inject

class ApiErrorMapper @Inject constructor() {

    fun map(errorApiResp: ErrorApiResp) = errorApiResp.run {
        ApiError(
            code = code ?: throw ParseException("Malformed API error response", 0),
            message = message
        )
    }
}