package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    fun add(number: Int) =
        intent {
            reduce {
                state.copy(number = state.number + number)
            }
        }

    fun test() =
        viewModelScope.doraLaunch {
            delay(1000L)
            println("tjrwn 현재 쓰레드 name ${Thread.currentThread().name}")
        }

    fun hideSnackBar() =
        intent {
            reduce {
                state.copy(shouldSnackBarShown = false)
            }
        }

    fun showSnackBar(clipboardText: String) = intent {
        reduce {
            state.copy(copiedText = clipboardText, shouldSnackBarShown = true)
        }
    }
}
