package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.CompleteEditFolder
import com.mashup.dorabangs.data.model.FailEditFolder
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val remoteDataSource: FolderRemoteDataSource,
) : FolderRepository {

    override suspend fun getFolders(): FolderList =
        remoteDataSource.getFolders()
            .toDomain()

    override suspend fun getFolderById(folderId: String): Folder =
        remoteDataSource.getFolderById(folderId).toDomain()

    override suspend fun createFolder(newFolderNameList: NewFolderNameList): DoraSampleResponse =
        runCatching {
            remoteDataSource.createFolder(folderList = newFolderNameList)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun editFolderName(
        newFolderName: NewFolderName,
        folderId: String,
    ): EditCompleteFolderInfo =
        runCatching {
            remoteDataSource.editFolderName(folderName = newFolderName, folderId = folderId).CompleteEditFolder()
        }.getOrElse { throwable ->
            val errorMsg = throwable.message.orEmpty()
            errorMsg.FailEditFolder()
        }
}
