package com.mashup.dorabangs.domain.usecase.folder

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetSavedLinksFromFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(
        needFetchUpdate: Boolean = false,
        cacheKey: String = "",
        folderId: String?,
        order: String,
        limit: Int,
        isRead: Boolean?,
        totalCount: (Int) -> Unit = {},
    ): Flow<PagingData<SavedLinkDetailInfo>> {
        return folderRepository.getLinksFromFolder(
            needFetchUpdate = needFetchUpdate,
            cacheKey = cacheKey,
            folderId = folderId,
            order = order.lowercase(Locale.ROOT),
            limit = limit,
            isRead = isRead,
            totalCount = totalCount,
        )
    }

    fun updatePostItem(
        page: Int,
        cacheKey: String,
        cachedKeyList: List<String>,
        item: SavedLinkDetailInfo,
    ) {
        folderRepository.updatePostItem(
            page = page,
            cacheKey = cacheKey,
            cachedKeyList = cachedKeyList,
            item = item,
        )
    }
}
