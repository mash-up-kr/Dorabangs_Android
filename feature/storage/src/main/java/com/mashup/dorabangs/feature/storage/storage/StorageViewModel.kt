package com.mashup.dorabangs.feature.storage.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.usecase.folder.DeleteFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.user.GetNeedToUpdateDataUseCase
import com.mashup.dorabangs.domain.usecase.user.SetNeedToUpdateDataUseCase
import com.mashup.dorabangs.feature.folders.model.FolderManageType
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
    private val getFolderListUseCase: GetFolderListUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val getNeedToUpdateDataUseCase: GetNeedToUpdateDataUseCase,
    private val setNeedToUpdateDataUseCase: SetNeedToUpdateDataUseCase,
) : ViewModel(), ContainerHost<StorageListState, StorageListSideEffect> {
    override val container = container<StorageListState, StorageListSideEffect>(StorageListState())

    init {
        getFolderList()
    }

    fun getFolderList() = viewModelScope.doraLaunch {
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

    fun deleteFolder(folderId: String, toastMsg: String) = viewModelScope.doraLaunch {
        val isSuccessDelete = deleteFolderUseCase(folderId = folderId)
        if (isSuccessDelete.isSuccess) {
            getFolderList()
            setVisibleDialog(false)
            setToastState(toastMsg)
        } else {
            // TODO - 에러처리
        }
    }

    fun setSelectFolderId(folderId: String) = intent {
        reduce {
            state.copy(selectedFolderId = folderId)
        }
    }

    fun setToastState(toastMsg: String) = intent {
        reduce { state.copy(toastState = state.toastState.copy(text = toastMsg, toastStyle = ToastStyle.CHECK)) }
        postSideEffect(StorageListSideEffect.ShowFolderRemoveToastSnackBar)
    }

    fun getNeedToUpdateData() = viewModelScope.doraLaunch {
        intent {
            val needToUpdateData = getNeedToUpdateDataUseCase()
            reduce { state.copy(needToUpdate = needToUpdateData) }
        }
    }

    fun setNeedToUpdateData(needToUpdate: Boolean) = viewModelScope.doraLaunch {
        intent {
            reduce { state.copy(needToUpdate = needToUpdate) }
        }
        setNeedToUpdateDataUseCase(needToUpdate)
    }

    fun setIsFirstEntry(isFirst: Boolean) = intent {
        reduce { state.copy(isFirstEntry = isFirst) }
    }

    fun updateFolderEditType(type: FolderManageType) = intent {
        reduce { state.copy(folderEditType = type) }
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
