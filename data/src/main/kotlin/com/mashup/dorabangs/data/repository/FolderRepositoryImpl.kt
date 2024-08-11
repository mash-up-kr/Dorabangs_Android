package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.utils.doraConvertKey
import com.mashup.dorabangs.data.model.toDomainModel
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraCreateFolderModel
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val remoteDataSource: FolderRemoteDataSource,
) : FolderRepository {

    private val pagingListCache = HashMap<String, PageData<List<SavedLinkDetailInfo>>>()

    override suspend fun getFolders(): FolderList =
        remoteDataSource.getFolders()
            .toDomain()

    override suspend fun getFolderById(folderId: String): Folder =
        remoteDataSource.getFolderById(folderId).toDomain()

    override suspend fun createFolder(newFolderNameList: NewFolderNameList): DoraCreateFolderModel =
        runCatching {
            val result =
                remoteDataSource.createFolder(folderList = newFolderNameList)
            result.toDomain()
        }.getOrElse { throwable ->
            DoraCreateFolderModel(
                isSuccess = false,
                errorMsg = throwable.message.orEmpty(),
            )
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
        needFetchUpdate: Boolean,
        cacheKey: String,
        folderId: String?,
        order: String,
        limit: Int,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<SavedLinkDetailInfo>> =
        doraPager(
            needFetchUpdate = needFetchUpdate,
            cachedList = pagingListCache,
            cacheKey = cacheKey,
            apiExecutor = { page ->
                remoteDataSource.getLinksFromFolder(
                    folderId = folderId,
                    page = page,
                    limit = limit,
                    order = order,
                    isRead = isRead,
                ).toDomain()
            },
            totalCount = { total -> totalCount(total) },
            cachingData = { item, page -> pagingListCache[doraConvertKey(page, cacheKey)] = item },
        ).flow

    override suspend fun getPostPageFromFolder(
        folderId: String?,
        page: Int,
        order: String,
        limit: Int,
        isRead: Boolean?,
    ): Posts =
        remoteDataSource.getLinksFromFolder(
            folderId = folderId,
            page = page,
            order = order,
            limit = limit,
            isRead = isRead,
        ).toDomainModel()

    override fun updatePostItem(
        page: Int,
        cacheKey: String,
        cachedKeyList: List<String>,
        item: SavedLinkDetailInfo,
    ) {
        val key = doraConvertKey(page, cacheKey)
        val updatedData = pagingListCache[key]?.data?.map { post ->
            if (post.id == item.id) {
                item
            } else {
                post
            }
        }
        cachedKeyList.forEach { currentKey ->
            val listKey = doraConvertKey(page, currentKey)
            val updatedPageData = updatedData?.let {
                pagingListCache[listKey]?.copy(data = it)
            }
            updatedPageData?.let { data ->
                pagingListCache[listKey] = data
            }
        }
    }
}
