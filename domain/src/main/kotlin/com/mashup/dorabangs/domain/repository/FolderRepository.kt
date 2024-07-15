package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.CreateFolder
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditFolder
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(createFolder: CreateFolder)
    suspend fun editFolderName(editFolder: EditFolder, folderId: String): EditCompleteFolderInfo
}
