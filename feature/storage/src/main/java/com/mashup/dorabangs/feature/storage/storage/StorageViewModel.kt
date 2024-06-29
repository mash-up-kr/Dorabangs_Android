package com.mashup.dorabangs.feature.storage.storage

import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.feature.storage.storage.model.StorageFolderItem
import com.mashup.dorabangs.feature.storage.storage.model.StorageListSideEffect
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor() : ViewModel(), ContainerHost<StorageListState, StorageListSideEffect> {
        override val container = container<StorageListState, StorageListSideEffect>(StorageListState())

    fun showEditFolderBottomSheet(item: StorageFolderItem) = intent {
        postSideEffect(sideEffect = StorageListSideEffect.showEditFolderBottomSheet)
    }
    }
