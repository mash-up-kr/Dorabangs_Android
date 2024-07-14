package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel

interface FolderRemoteDataSource {

    suspend fun getFolders(): FolderListResponseModel

    suspend fun getFolderById(folderId: String): FolderResponseModel?
}
