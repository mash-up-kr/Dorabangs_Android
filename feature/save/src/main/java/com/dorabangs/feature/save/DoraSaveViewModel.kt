package com.dorabangs.feature.save

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.posts.SaveLinkUseCase
import com.mashup.dorabangs.domain.usecase.save.DoraUrlCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        val copiedUrl = savedStateHandle.get<String>("copiedUrl").orEmpty()
        if (copiedUrl.isNotBlank()) checkUrl(copiedUrl)
    }

    fun getFolderList() = viewModelScope.doraLaunch {
        intent { reduce { state.copy(isLoading = true) } }
        val list = getFolderListUseCase.invoke()
        val firstItem = listOf(
            SelectableFolder(
                id = null,
                name = ADD_NEW_FOLDER,
                type = FolderType.NOTHING,
                createdAt = null,
                postCount = null,
                isSelected = false,
            ),
        )
        val newList = (filterDefaultList(list) + list.customFolders).let { mergedList ->
            mergedList.mapIndexed { index, item ->
                SelectableFolder(
                    id = item.id,
                    name = item.name,
                    type = item.folderType,
                    createdAt = item.createdAt,
                    postCount = item.postCount,
                    isSelected = index == 0,
                )
            }
        }
        intent {
            reduce {
                state.copy(
                    folderList = firstItem + newList,
                )
            }
        }
    }.invokeOnCompletion {
        intent {
            reduce {
                state.copy(isLoading = false)
            }
        }
    }

    /**
     * 서버 통신해서 나온 default folders 중에서 나중에 읽을 링크만 사용
     */
    private fun filterDefaultList(list: FolderList) =
        listOf(
            list.defaultFolders.firstOrNull { it.folderType == FolderType.DEFAULT }
                ?: error("여기는 서버 잘못임 우리 탓 아님 ㄹㅇ"),
        )

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

    /**
     * 0번 째 있는 새 폴더 추가는 클릭이 안됨
     */
    fun updateSelectedFolder(index: Int) = intent {
        reduce {
            if (index != 0) {
                state.folderList.mapIndexed { listIndex, item ->
                    if (listIndex == index) {
                        item.copy(isSelected = true)
                    } else item.copy(isSelected = false)
                }.let { newList ->
                    state.copy(
                        folderList = newList,
                    )
                }
            } else {
                state
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
        postSideEffect(DoraSaveSideEffect.FinishSaveLink)
    }

    companion object {
        private const val ADD_NEW_FOLDER = "새 폴더 추가"
    }
}
