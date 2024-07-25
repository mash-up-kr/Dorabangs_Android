package com.mashup.dorabangs.domain.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.DoraCreateFolderModel
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(newFolderNameList: NewFolderNameList): DoraCreateFolderModel
    suspend fun editFolderName(newFolderName: NewFolderName, folderId: String): DoraSampleResponse
    suspend fun deleteFolder(folderId: String): DoraSampleResponse
    suspend fun getLinksFromFolder(
        folderId: String?,
        order: String,
        isRead: Boolean?,
    ): Flow<PagingData<SavedLinkDetailInfo>>
}
