package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.repository.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val remoteDataSource: FolderRemoteDataSource,
) : FolderRepository {

    override suspend fun getFolders(): FolderList =
        remoteDataSource.getFolders()
            .toDomain()

    override suspend fun getFolderById(folderId: String): Folder? =
        remoteDataSource.getFolderById(folderId)
            ?.toDomain()
}
