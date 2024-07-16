package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.NewFolderCreation
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.FolderEdition
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(newFolderCreation: NewFolderCreation)
    suspend fun editFolderName(folderEdition: FolderEdition, folderId: String): EditCompleteFolderInfo
}
