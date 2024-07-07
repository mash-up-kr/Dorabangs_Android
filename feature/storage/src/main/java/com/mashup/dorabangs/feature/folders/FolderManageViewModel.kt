package com.mashup.dorabangs.feature.folders

import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.feature.folders.model.FolderManageState
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

class FolderManageViewModel @Inject constructor() : ViewModel(),
    ContainerHost<FolderManageState, FolderManageSideEffect> {
    override val container = container<FolderManageState, FolderManageSideEffect>(FolderManageState())

    fun setFolderManageType(name: String) = intent {
        reduce {
            when(name) {
                FolderManageType.CREATE.name -> state.copy(type = FolderManageType.CREATE)
                FolderManageType.CHANGE.name -> state.copy(type = FolderManageType.CHANGE)
                else -> state.copy(type = FolderManageType.CREATE)
            }
        }
    }

}
