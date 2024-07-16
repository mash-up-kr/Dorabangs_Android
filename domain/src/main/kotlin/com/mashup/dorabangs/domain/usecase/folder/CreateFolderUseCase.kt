package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.NewFolderCreation
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class CreateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(folderList: NewFolderCreation) {
        return folderRepository.createFolder(newFolderCreation = folderList)
    }
}
