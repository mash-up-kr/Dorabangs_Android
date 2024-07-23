package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.usecase.folder.GetSavedLinksFromFolderUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPosts
import com.mashup.dorabangs.domain.usecase.posts.PatchPostInfoUseCase
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StorageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val savedLinksFromFolderUseCase: GetSavedLinksFromFolderUseCase,
    private val patchPostInfoUseCase: PatchPostInfoUseCase,
    private val getPostsUseCase: GetPosts,
) : ViewModel(), ContainerHost<StorageDetailState, StorageDetailSideEffect> {
    override val container = container<StorageDetailState, StorageDetailSideEffect>(StorageDetailState())

    fun setFolderInfo(folderItem: Folder) = intent {
        reduce {
            state.copy(
                folderId = folderItem.id,
                title = folderItem.name,
                postCount = folderItem.postCount,
                folderType = folderItem.folderType,
            )
        }
        fetchSavedLinkFromType(
            type = folderItem.folderType,
            folderId = folderItem.id,
        )
    }

    /**
     * 폴더 타입별 API 호출 분기 처리
     */
    private fun fetchSavedLinkFromType(
        type: FolderType = FolderType.ALL,
        folderId: String? = "",
        order: String = StorageDetailSort.ASC.name,
        isRead: Boolean? = null,
    ) {
        when (type) {
            FolderType.ALL -> getSavedLinkFromDefaultFolder(order = order, favorite = false, isRead = isRead)
            FolderType.FAVORITE -> getSavedLinkFromDefaultFolder(order = order, favorite = true, isRead = isRead)
            else -> getSavedLinkFromCustomFolder(folderId = folderId, order = order, isRead = isRead)
        }
    }

    /**
     * 사용자 지정 folder links
     */
    private fun getSavedLinkFromCustomFolder(
        folderId: String?,
        order: String = StorageDetailSort.ASC.name,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        val pagingData = savedLinksFromFolderUseCase.invoke(folderId = folderId, order = order, isRead = isRead)
            .cachedIn(viewModelScope).map { pagedData ->
                pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = PagingData.empty(),
            )
        intent {
            reduce {
                state.copy(pagingList = pagingData)
            }
        }
    }

    /**
     * default folder links
     */
    private fun getSavedLinkFromDefaultFolder(
        order: String = StorageDetailSort.ASC.name,
        favorite: Boolean = false,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        val pagingData =
            getPostsUseCase.invoke(order = order, favorite = favorite, isRead = isRead)
                .cachedIn(viewModelScope).map { pagedData ->
                    pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty(),
                )
        intent {
            reduce {
                state.copy(pagingList = pagingData)
            }
        }
    }

    /**
     * 탭 변환
     */
    fun changeSelectedTabIdx(selectedIdx: Int) = viewModelScope.launch {
        intent {
            val isRead = if (selectedIdx == 0) null else false
            fetchSavedLinkFromType(
                type = state.folderType,
                folderId = state.folderId,
                order = state.isLatestSort.name,
                isRead = isRead,
            )
            reduce {
                state.copy(
                    selectedTabIdx = selectedIdx,
                )
            }
        }
    }

    /**
     * 정렬 변환
     */
    fun clickFeedSort(item: StorageDetailSort) = viewModelScope.launch {
        intent {
            val isRead = if (state.selectedTabIdx == 0) null else false
            fetchSavedLinkFromType(
                type = state.folderType,
                folderId = state.folderId,
                order = item.name,
                isRead = isRead,
            )
            reduce {
                state.copy(
                    isLatestSort = item,
                )
            }
        }
    }

    /**
     * 즐겨찾기 추가
     * 낙관적 Update추가, 스크롤 위치가 왜 변할까?
     */
    fun addFavoriteItem(postId: String, isFavorite: Boolean) = viewModelScope.doraLaunch {
        intent {
            val postInfo = PostInfo(isFavorite = !isFavorite)
            patchPostInfoUseCase(
                postId = postId,
                postInfo = postInfo,
            )
            reduce {
                val updatedCardList = state.pagingList.map { pagingData ->
                    pagingData.map { currentItem ->
                        if (currentItem.id == postId) {
                            currentItem.copy(isFavorite = !isFavorite)
                        } else {
                            currentItem
                        }
                    }
                }
                state.copy(pagingList = updatedCardList)
            }
        }
    }
}
