package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class GetFolderListUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {

    suspend operator fun invoke(): FolderList {
        return folderRepository.getFolders()
    }
}
