package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.SavedLinkListFromFolder
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class GetSavedLinksFromFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderId: String, page: Int, order: String, unread: Boolean): SavedLinkListFromFolder {
        return folderRepository.getLinksFromFolder(
            folderId = folderId,
            page = page,
            order = order,
            unread = unread
        )
    }
}