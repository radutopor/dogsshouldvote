package com.radutopor.dogsshouldvote.ui.common

/**
 * Once the consume block has run, the closure will not be executed again.
 * The ViewState must be updated with a new instance of ViewEvent() for the
 * closure to run again.
 */
data class ViewEvent<T>(private val value: T? = null) {
    var consumed = false
        private set

    fun consume(it: (value: T) -> Unit) {
        if (!consumed && value != null) {
            it(value)
            consumed = true
        }
    }
}