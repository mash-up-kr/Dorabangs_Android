package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.EditFolderNameResponseModel
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.domain.model.FolderEdition
import com.mashup.dorabangs.domain.model.NewFolderCreation

interface FolderRemoteDataSource {

    suspend fun getFolders(): FolderListResponseModel

    suspend fun getFolderById(folderId: String): FolderResponseModel

    suspend fun createFolder(folderList: NewFolderCreation)

    suspend fun editFolderName(folderName: FolderEdition, folderId: String): EditFolderNameResponseModel
}
