package com.mashup.dorabangs.domain.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.DoraCreateFolderModel
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    suspend fun getFolders(): FolderList
    suspend fun getFolderById(folderId: String): Folder
    suspend fun createFolder(newFolderNameList: NewFolderNameList): DoraCreateFolderModel
    suspend fun editFolderName(newFolderName: NewFolderName, folderId: String): DoraSampleResponse
    suspend fun deleteFolder(folderId: String): DoraSampleResponse
    suspend fun getLinksFromFolder(
        needFetchUpdate: Boolean = false,
        cacheKey: String = "",
        folderId: String?,
        order: String,
        limit: Int,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<SavedLinkDetailInfo>>

    suspend fun getLinksFromFolderRemote(
        needFetchUpdate: Boolean,
        folderId: String?,
        order: String,
        limit: Int,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<SavedLinkDetailInfo>>
    suspend fun getPostPageFromFolder(
        folderId: String?,
        page: Int,
        order: String,
        limit: Int,
        isRead: Boolean?,
    ): Posts

    fun updatePostItem(
        page: Int,
        cacheKey: String,
        cachedKeyList: List<String>,
        item: SavedLinkDetailInfo,
    )
}
