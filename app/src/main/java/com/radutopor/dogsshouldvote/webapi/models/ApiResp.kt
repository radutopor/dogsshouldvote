package com.radutopor.dogsshouldvote.webapi.models

data class ApiResp<T>(
    val status: String,
    val message: T,
    val code: Int?,
)