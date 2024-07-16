package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.CreateCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(newFolderNameList: NewFolderNameList): CreateCompleteFolderInfo
    suspend fun editFolderName(newFolderName: NewFolderName, folderId: String): EditCompleteFolderInfo
}
