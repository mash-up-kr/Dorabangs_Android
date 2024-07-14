package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class GetFolderById @Inject constructor(
    private val folderRepository: FolderRepository,
) {

    suspend operator fun invoke(folderId: String): Folder? {
        return folderRepository.getFolderById(folderId)
    }
}
