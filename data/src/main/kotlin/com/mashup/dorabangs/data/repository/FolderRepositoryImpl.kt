package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
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
    ): DoraSampleResponse =
        runCatching {
            remoteDataSource.editFolderName(folderName = newFolderName, folderId = folderId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun deleteFolder(folderId: String): DoraSampleResponse =
        runCatching {
            remoteDataSource.deleteFolder(folderId = folderId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun getLinksFromFolder(
        folderId: String?,
        order: String,
        unread: Boolean,
    ): Flow<PagingData<SavedLinkDetailInfo>> =
        doraPager { page ->
            remoteDataSource.getLinksFromFolder(
                folderId = folderId,
                page = page,
                order = order,
                unread = unread,
            ).toDomain()
        }.flow
}
