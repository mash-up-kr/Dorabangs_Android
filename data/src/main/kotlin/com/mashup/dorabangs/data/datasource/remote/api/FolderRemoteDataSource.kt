package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.CreateFolderResponse
import com.mashup.dorabangs.data.model.EditFolderNameResponse
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.domain.model.CreateFolder
import com.mashup.dorabangs.domain.model.EditFolder

interface FolderRemoteDataSource {

    suspend fun getFolders(): FolderListResponseModel

    suspend fun getFolderById(folderId: String): FolderResponseModel

    suspend fun createFolder(folderList: CreateFolder): CreateFolderResponse

    suspend fun editFolderName(folderName: EditFolder): EditFolderNameResponse
}
