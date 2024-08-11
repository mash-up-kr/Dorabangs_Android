package com.mashup.dorabangs.domain.usecase.folder

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetSavedLinksRemoteUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(
        needFetchUpdate: Boolean = false,
        folderId: String?,
        order: String,
        limit: Int,
        isRead: Boolean?,
        totalCount: (Int) -> Unit = {},
    ): Flow<PagingData<SavedLinkDetailInfo>> {
        return folderRepository.getLinksFromFolderRemote(
            needFetchUpdate = needFetchUpdate,
            folderId = folderId,
            order = order.lowercase(Locale.ROOT),
            limit = limit,
            isRead = isRead,
            totalCount = totalCount,
        )
    }
}
