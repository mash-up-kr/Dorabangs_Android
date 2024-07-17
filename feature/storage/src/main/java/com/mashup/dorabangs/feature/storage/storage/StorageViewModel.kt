package com.mashup.dorabangs.feature.storage.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.usecase.folder.DeleteFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderList
import com.mashup.dorabangs.feature.storage.storage.model.StorageListSideEffect
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFolderListUseCase: GetFolderList,
    private val deleteFolderUseCase: DeleteFolderUseCase,
) : ViewModel(), ContainerHost<StorageListState, StorageListSideEffect> {
    override val container = container<StorageListState, StorageListSideEffect>(StorageListState())

    init {
        getFolderList()
    }

    private fun getFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderListUseCase()
        intent {
            reduce {
                state.copy(
                    defaultStorageFolderList = folderList.defaultFolders,
                    customStorageFolderList = folderList.customFolders,
                )
            }
        }
    }

    fun deleteFolder(folderId: String) = viewModelScope.doraLaunch {
        val isSuccessDelete = deleteFolderUseCase(folderId = folderId)
        if (isSuccessDelete.isSuccess) {
            getFolderList()
            setVisibleDialog(false)
        } else {
            // TODO - 에러처리
        }
    }

    fun setSelectFolderId(folderId: String) = intent {
        reduce {
            state.copy(selectedFolderId = folderId)
        }
    }

    fun setVisibleMoreButtonBottomSheet(visible: Boolean, isNavigate: Boolean = false) = intent {
        reduce {
            state.copy(isShowMoreButtonSheet = visible)
        }
        if (isNavigate) postSideEffect(StorageListSideEffect.NavigateToFolderManage)
    }

    fun setVisibleDialog(visible: Boolean) = intent {
        reduce {
            state.copy(isShowDialog = visible)
        }
    }
}
