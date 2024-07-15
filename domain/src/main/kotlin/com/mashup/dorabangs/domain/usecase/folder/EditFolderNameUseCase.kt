package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditFolder
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class EditFolderNameUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderName: EditFolder, folderId: String): EditCompleteFolderInfo {
        return folderRepository.editFolderName(editFolder = folderName, folderId = folderId)
    }
}
