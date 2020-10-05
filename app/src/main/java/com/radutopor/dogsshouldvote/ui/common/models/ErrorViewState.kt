package com.radutopor.dogsshouldvote.ui.common.models

data class ErrorViewState(
    val visibility: Boolean = false,
    val text: String = "",
    val onRetryClick: () -> Unit = {},
)