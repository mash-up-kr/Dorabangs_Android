package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.FolderRename
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class EditFolderNameUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderName: FolderRename, folderId: String): EditCompleteFolderInfo {
        return folderRepository.editFolderName(folderRename = folderName, folderId = folderId)
    }
}
