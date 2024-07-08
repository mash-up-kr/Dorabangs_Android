package com.mashup.dorabangs.feature.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.CreateFolder
import com.mashup.dorabangs.domain.model.EditFolder
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.EditFolderNameUseCase
import com.mashup.dorabangs.feature.folders.model.FolderManageState
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

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

    fun createOrEditFolder(folderName: String, folderType: FolderManageType) = viewModelScope.doraLaunch {
        when (folderType) {
            FolderManageType.CREATE -> {
                createFolderUseCase.invoke(folderList = CreateFolder(names = listOf(folderName)))
            }
            FolderManageType.CHANGE -> {
                editFolderNameUseCase.invoke(folderName = EditFolder(name = folderName))
            }
        }
        //에러 처리하고 postsideEffect
        intent {
            postSideEffect(FolderManageSideEffect.NavigateToStorage)
        }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(folderName = folderName)
        }
    }
}
