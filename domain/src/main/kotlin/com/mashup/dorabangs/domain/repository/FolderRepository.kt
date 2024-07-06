package com.mashup.dorabangs.domain.repository

import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList

interface FolderRepository {

    suspend fun getFolders(): FolderList

    suspend fun getFolderById(folderId: String): Folder?
}