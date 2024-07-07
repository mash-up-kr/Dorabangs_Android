package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.data.network.service.FolderService
import javax.inject.Inject

class FolderRemoteDataSourceImpl @Inject constructor(
    private val folderService: FolderService,
) : FolderRemoteDataSource {

    override suspend fun getFolders(): FolderListResponseModel =
        folderService.getFolders()
            .getData() ?: FolderListResponseModel()

    override suspend fun getFolderById(folderId: String): FolderResponseModel? =
        folderService.getFolderById(folderId)
            .getData()
}
