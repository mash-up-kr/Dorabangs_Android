package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.usecase.folder.GetSavedLinksFromFolderUseCase
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageDetailViewModel @Inject constructor(
    private val savedLinksFromFolderUseCase: GetSavedLinksFromFolderUseCase,
) : ViewModel(), ContainerHost<StorageDetailState, StorageDetailSideEffect> {
    override val container = container<StorageDetailState, StorageDetailSideEffect>(StorageDetailState())

    fun getSavedLinkFromFolder(folderId: String, order: String, unread: Boolean) =
        viewModelScope.doraLaunch {
            val pagingData = savedLinksFromFolderUseCase.invoke(folderId = folderId, order = order, unread = unread)
                .cachedIn(viewModelScope).map { pagedData ->
                    pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty(),
                )
            intent {
                reduce { state.copy(pagingList = pagingData) }
            }
        }

    fun changeSelectedTabIdx(selectedIdx: Int) = intent {
        reduce {
            state.copy(
                selectedIdx = selectedIdx,
            )
        }
    }

    fun clickFeedSort(item: StorageDetailSort) = intent {
        reduce {
            state.copy(
                isLatestSort = item == StorageDetailSort.LATEST,
            )
        }
    }
}
