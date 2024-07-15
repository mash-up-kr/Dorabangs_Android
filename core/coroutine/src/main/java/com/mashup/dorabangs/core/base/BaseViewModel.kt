package com.mashup.dorabangs.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _error: Channel<Throwable> = Channel()

    init {
        _error.receiveAsFlow()
            .onEach(::handleErrors)
            .launchIn(viewModelScope)
    }

    val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("Dorabangs", "dorabangs exception: ${throwable.message}", throwable)
            viewModelScope.launch { _error.send(throwable) }
        }

    inline fun doraLaunch(crossinline block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(coroutineExceptionHandler) {
            block.invoke(this)
        }

    abstract fun handleErrors(error: Throwable)
}

