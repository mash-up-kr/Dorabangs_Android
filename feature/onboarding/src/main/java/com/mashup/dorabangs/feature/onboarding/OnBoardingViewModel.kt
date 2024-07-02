package com.mashup.dorabangs.feature.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

    fun onClickKeyword(index: Int) = intent {
        reduce {
            if (state.selectedIndex.contains(index)) {
                state.copy(selectedIndex = state.selectedIndex.minus(index))
            } else {
                state.copy(selectedIndex = state.selectedIndex.plus(index))
            }
        }
    }
}