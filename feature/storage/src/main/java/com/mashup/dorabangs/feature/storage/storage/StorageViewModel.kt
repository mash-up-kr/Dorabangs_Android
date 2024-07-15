package com.mashup.dorabangs.feature.storage.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderList
import com.mashup.dorabangs.feature.storage.storage.model.StorageListSideEffect
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFolderListUseCase: GetFolderList
) : ViewModel(), ContainerHost<StorageListState, StorageListSideEffect> {
    override val container = container<StorageListState, StorageListSideEffect>(StorageListState())

    init {
        getFolderList()
    }


    private fun getFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderListUseCase()
        intent {
            state.copy(
                defaultStorageFolderList = folderList.defaultFolders,
                customStorageFolderList = folderList.customFolders
            )
        }
    }

    fun setVisibleMoreButtonBottomSheet(visible: Boolean) = intent {
        reduce {
            state.copy(isShowMoreButtonSheet = visible)
        }
    }

    fun setVisibleDialog(visible: Boolean) = intent {
        reduce {
            state.copy(isShowDialog = visible)
        }
    }
}
