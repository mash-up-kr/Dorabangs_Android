package com.dorabangs.feature.save

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.posts.SaveLinkUseCase
import com.mashup.dorabangs.domain.usecase.save.DoraUrlCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DoraSaveViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val urlCheckUseCase: DoraUrlCheckUseCase,
    private val getFolderListUseCase: GetFolderListUseCase,
    private val postSaveLinkUseCase: SaveLinkUseCase,
) : ViewModel(), ContainerHost<DoraSaveState, DoraSaveSideEffect> {

    init {
        getFolderList()
        val copiedUrl = savedStateHandle.get<String>("copiedUrl").orEmpty()
        if (copiedUrl.isNotBlank()) checkUrl(copiedUrl)
    }

    private fun getFolderList() = viewModelScope.doraLaunch {
        val list = getFolderListUseCase.invoke()
        val newList = (list.defaultFolders + list.customFolders).let { mergedList ->
            mergedList.mapIndexed { index, item ->
                SelectableFolder(
                    id = item.id,
                    name = item.name,
                    type = item.type,
                    createdAt = item.createdAt,
                    postCount = item.postCount,
                    isSelected = index == 0,
                )
            }
        }
        intent {
            reduce {
                state.copy(
                    folderList = newList,
                )
            }
        }
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
            DoraSaveSideEffect.ClickItem(index = index),
        )
    }

    fun updateList(index: Int) = intent {
        reduce {
            state.folderList.mapIndexed { listIndex, item ->
                if (listIndex == index - 1) {
                    item.copy(isSelected = true)
                } else item.copy(isSelected = false)
            }.let { newList ->
                state.copy(
                    folderList = newList,
                )
            }
        }
    }

    fun clickSaveButton() = intent {
        postSideEffect(
            DoraSaveSideEffect.ClickSaveButton(id = state.folderList.find { it.isSelected }?.id.orEmpty()),
        )
    }

    fun saveLink(id: String) = intent {
        postSaveLinkUseCase.invoke(Link(folderId = id, url = state.urlLink))
    }
}
