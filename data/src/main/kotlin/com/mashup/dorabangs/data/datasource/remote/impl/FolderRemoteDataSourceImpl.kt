package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.CreateFolderResponse
import com.mashup.dorabangs.data.model.EditFolderNameResponse
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.data.model.toData
import com.mashup.dorabangs.data.network.service.FolderService
import com.mashup.dorabangs.domain.model.CreateFolder
import com.mashup.dorabangs.domain.model.EditFolder
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

    override suspend fun createFolder(folderList: CreateFolder): CreateFolderResponse =
        folderService.createFolder(folderList.toData()).getData() ?: CreateFolderResponse()

    override suspend fun editFolderName(folderName: EditFolder): EditFolderNameResponse =
        folderService.editFolderName(folderName.toData()).getData() ?: EditFolderNameResponse()
}
