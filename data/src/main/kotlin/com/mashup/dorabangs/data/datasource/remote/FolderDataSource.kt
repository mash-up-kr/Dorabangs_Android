package com.mashup.dorabangs.data.datasource.remote

import com.mashup.dorabangs.data.model.CreateFolderRequest
import com.mashup.dorabangs.data.model.CreateFolderResponse
import com.mashup.dorabangs.data.network.service.FolderService
import javax.inject.Inject

class FolderDataSource @Inject constructor(
    private val folderService: FolderService,
) {
    suspend fun createFolder(createFolder: CreateFolderRequest): CreateFolderResponse {
        return folderService.createFolder(createFolderRequest = createFolder)
    }
}
