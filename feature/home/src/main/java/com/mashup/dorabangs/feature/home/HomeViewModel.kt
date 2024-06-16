package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    fun add(number: Int) = intent {
        reduce {
            state.copy(state.number + number)
        }
    }
}
