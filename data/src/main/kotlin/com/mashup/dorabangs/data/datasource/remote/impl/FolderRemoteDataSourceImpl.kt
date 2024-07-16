package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.EditFolderNameResponseModel
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import com.mashup.dorabangs.data.model.toData
import com.mashup.dorabangs.data.network.service.FolderService
import com.mashup.dorabangs.domain.model.NewFolderCreation
import com.mashup.dorabangs.domain.model.FolderEdition
import javax.inject.Inject

class FolderRemoteDataSourceImpl @Inject constructor(
    private val folderService: FolderService,
) : FolderRemoteDataSource {

    override suspend fun getFolders(): FolderListResponseModel =
        folderService.getFolders()

    override suspend fun getFolderById(folderId: String): FolderResponseModel =
        folderService.getFolderById(folderId)

    override suspend fun createFolder(folderList: NewFolderCreation) {
        folderService.createFolder(folderList.toData())
    }

    override suspend fun editFolderName(folderName: FolderEdition, folderId: String): EditFolderNameResponseModel =
        folderService.editFolderName(folderId, folderName.toData())
}
