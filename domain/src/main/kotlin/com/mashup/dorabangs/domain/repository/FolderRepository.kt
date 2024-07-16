package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.CreateCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderRename
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderCreation

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(newFolderCreation: NewFolderCreation): CreateCompleteFolderInfo
    suspend fun editFolderName(folderRename: FolderRename, folderId: String): EditCompleteFolderInfo
}
