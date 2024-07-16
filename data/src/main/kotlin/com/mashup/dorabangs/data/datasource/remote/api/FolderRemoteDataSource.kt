package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.EditFolderNameResponseModel
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList

interface FolderRemoteDataSource {

    suspend fun getFolders(): FolderListResponseModel

    suspend fun getFolderById(folderId: String): FolderResponseModel

    suspend fun createFolder(folderList: NewFolderNameList)

    suspend fun editFolderName(folderName: NewFolderName, folderId: String): EditFolderNameResponseModel
}
