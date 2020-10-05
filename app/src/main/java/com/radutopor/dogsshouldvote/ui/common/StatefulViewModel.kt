package com.radutopor.dogsshouldvote.ui.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class StatefulViewModel<T> : ViewModel() {

    private val liveData = MutableLiveData(initialState)

    abstract val initialState: T

    var state
        get() = liveData.value ?: initialState
        protected set(value) {
            liveData.value = value
        }

    val stateObservable: LiveData<T> = liveData

    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) =
        liveData.observe(owner, observer)
}