package com.radutopor.dogsshouldvote.webapi

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken.getParameterized
import com.radutopor.dogsshouldvote.webapi.models.ApiResp
import com.radutopor.dogsshouldvote.webapi.models.ErrorApiResp
import retrofit2.HttpException

fun HttpException.getErrorApiResp(): ErrorApiResp? =
    response()?.errorBody()?.string()?.let {
        Gson().fromJson(it, getParameterized(ApiResp::class.java, String::class.java).type)
    }