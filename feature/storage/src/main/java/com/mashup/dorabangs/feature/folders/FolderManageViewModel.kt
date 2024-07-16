package com.mashup.dorabangs.feature.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.FolderEdition
import com.mashup.dorabangs.domain.model.NewFolderCreation
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.EditFolderNameUseCase
import com.mashup.dorabangs.feature.folders.model.FolderManageState
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FolderManageViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase,
    private val editFolderNameUseCase: EditFolderNameUseCase,
) : ViewModel(), ContainerHost<FolderManageState, FolderManageSideEffect> {
    override val container = container<FolderManageState, FolderManageSideEffect>(FolderManageState())

    fun setFolderManageType(name: String) = intent {
        reduce {
            when (name) {
                FolderManageType.CREATE.name -> state.copy(type = FolderManageType.CREATE)
                FolderManageType.CHANGE.name -> state.copy(type = FolderManageType.CHANGE)
                else -> state.copy(type = FolderManageType.CREATE)
            }
        }
    }

    fun createOrEditFolder(folderName: String, folderType: FolderManageType, folderId: String = "") = viewModelScope.doraLaunch {
        val (isSuccess, message) = when (folderType) {
            FolderManageType.CREATE -> {
                val isSuccessCreateFolder = createFolderUseCase.invoke(folderList = NewFolderCreation(names = listOf(folderName)))
                isSuccessCreateFolder.isSuccess to isSuccessCreateFolder.errorMsg
            }
            FolderManageType.CHANGE -> {
                // TODO - Folder목록 조회 API 붙인 후 folderId 연결
                val editFolderInfo = editFolderNameUseCase.invoke(folderName = FolderEdition(name = folderName), folderId = folderId)
                editFolderInfo.completeFolderInfo.id.isNotEmpty() to editFolderInfo.errorMsg
            }
        }
        if (isSuccess) {
            intent {
                postSideEffect(FolderManageSideEffect.NavigateToStorage)
            }
        } else {
            setTextHelperEnable(isEnable = true, helperMessage = message)
        }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(folderName = folderName)
        }
    }

    fun setTextHelperEnable(isEnable: Boolean, helperMessage: String) = intent {
        reduce { state.copy(helperEnable = isEnable, helperMessage = helperMessage) }
    }
}
