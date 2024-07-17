package com.mashup.dorabangs.feature.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
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

    fun createOrEditFolder(folderName: String, folderType: FolderManageType, folderId: String) = viewModelScope.doraLaunch {
        val isSuccessNewFolder = when (folderType) {
            FolderManageType.CREATE -> {
                createFolderUseCase.invoke(folderList = NewFolderNameList(names = listOf(folderName)))
            }
            FolderManageType.CHANGE -> {
                editFolderNameUseCase.invoke(folderName = NewFolderName(name = folderName), folderId = folderId)
            }
        }
        if (isSuccessNewFolder.isSuccess) {
            intent {
                postSideEffect(FolderManageSideEffect.NavigateToStorage)
            }
        } else {
            setTextHelperEnable(isEnable = true, helperMessage = isSuccessNewFolder.errorMsg)
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
