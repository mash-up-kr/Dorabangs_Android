package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.CompleteEditFolder
import com.mashup.dorabangs.data.model.FailEditFolder
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.domain.model.CreateCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderEdition
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderCreation
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

    override suspend fun createFolder(newFolderCreation: NewFolderCreation): CreateCompleteFolderInfo =
        runCatching {
            remoteDataSource.createFolder(folderList = newFolderCreation)
            CreateCompleteFolderInfo(isSuccess = true)
        }.getOrElse { throwable ->
            CreateCompleteFolderInfo(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun editFolderName(
        folderEdition: FolderEdition,
        folderId: String,
    ): EditCompleteFolderInfo =
        runCatching {
            remoteDataSource.editFolderName(folderName = folderEdition, folderId = folderId).CompleteEditFolder()
        }.getOrElse { throwable ->
            val errorMsg = throwable.message.orEmpty()
            errorMsg.FailEditFolder()
        }
}
