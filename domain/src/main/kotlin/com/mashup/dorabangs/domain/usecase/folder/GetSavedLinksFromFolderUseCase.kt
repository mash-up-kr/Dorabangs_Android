package com.mashup.dorabangs.domain.usecase.folder

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLinksFromFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderId: String, order: String, unread: Boolean): Flow<PagingData<SavedLinkDetailInfo>> {
        return folderRepository.getLinksFromFolder(
            folderId = folderId,
            order = order,
            unread = unread,
        )
    }
}
