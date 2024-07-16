package com.dorabangs.feature.save

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.usecase.save.DoraUrlCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.postSideEffect

@HiltViewModel
class DoraSaveViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val urlCheckUseCase: DoraUrlCheckUseCase,
) : ViewModel(), ContainerHost<DoraSaveState, DoraSaveSideEffect> {

    init {
        val copiedUrl = savedStateHandle.get<String>("copiedUrl").orEmpty()
        if (copiedUrl.isNotBlank()) checkUrl(copiedUrl)
    }

    override val container: Container<DoraSaveState, DoraSaveSideEffect> =
        container(DoraSaveState())

    fun checkUrl(urlLink: String) = viewModelScope.doraLaunch {
        val checkResult = urlCheckUseCase.invoke(urlLink)
        intent {
            reduce {
                state.copy(
                    title = checkResult.title,
                    urlLink = checkResult.urlLink,
                    thumbnailUrl = checkResult.thumbnailUrl,
                    isShortLink = checkResult.isShortLink,
                    isError = checkResult.isError,
                )
            }
        }
    }

    fun clickSelectableItem(index: Int) = intent {
        postSideEffect(
            DoraSaveSideEffect.ClickItem(index = index)
        )
    }

    fun updateList(index: Int) = intent {
        reduce {
            state.folderList.mapIndexed { listIndex, item ->
                if (listIndex == index) item.copy(isSelected = true)
                else item.copy(isSelected = false)
            }.let { newList ->
                state.copy(
                    folderList = newList,
                )
            }
        }
    }
}
