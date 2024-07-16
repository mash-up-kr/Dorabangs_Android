package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderId: String): DoraSampleResponse {
        return folderRepository.deleteFolder(folderId = folderId)
    }
}