package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.feature.storage.storage.model.StorageFolderItem
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageDetailViewModel @Inject constructor() : ViewModel(), ContainerHost<StorageDetailState, StorageDetailSideEffect> {
        override val container = container<StorageDetailState, StorageDetailSideEffect>(StorageDetailState())


    fun changeSelectedTabIdx(selectedIdx: Int) = intent {
        reduce {
            state.copy(
                selectedIdx = selectedIdx
            )
        }
    }

    fun clickFeedSort(item: StorageDetailSort) = intent {
        reduce {
            state.copy(
                isLatestSort = item == StorageDetailSort.LATEST
            )
        }
    }
}
