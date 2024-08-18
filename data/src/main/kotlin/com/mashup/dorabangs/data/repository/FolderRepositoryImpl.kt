package com.mashup.dorabangs.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mashup.dorabangs.data.database.PostDatabase
import com.mashup.dorabangs.data.database.RemoteKeys
import com.mashup.dorabangs.data.database.toLocalEntity
import com.mashup.dorabangs.data.database.toSavedLinkDetailInfo
import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.model.toDomainModel
import com.mashup.dorabangs.data.utils.doraConvertKey
import com.mashup.dorabangs.data.pagingsource.PostRemoteMediator
import com.mashup.dorabangs.data.utils.PAGING_SIZE
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraCreateFolderModel
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.NewFolderName
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val remoteDataSource: FolderRemoteDataSource,
    private val database: PostDatabase,
) : FolderRepository {

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
        ).flow

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getLinksFromFolderRemote(
        needFetchUpdate: Boolean,
        folderId: String?,
        order: String,
        limit: Int,
        favorite: Boolean?,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<SavedLinkDetailInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = PAGING_SIZE,
            ),
            remoteMediator = PostRemoteMediator(
                database = database,
                needFetchUpdate = needFetchUpdate,
                totalCount = { total -> totalCount(total) },
                toLocalEntity = { post -> post.toLocalEntity() },
                getRemoteKey = { post -> RemoteKeys(postId = post.id.orEmpty()) },
                apiExecutor = { page ->
                    remoteDataSource.getLinksFromFolder(
                        folderId = folderId,
                        page = page,
                        limit = limit,
                        order = order,
                        isRead = isRead,
                    ).toDomain()
                },
            ),
            pagingSourceFactory = {
                if (order == "asc") {
                    database.postDao().getAllPostsOrderedByAsc(isRead = isRead == null)
                } else database.postDao().getAllPostsOrderedByDesc(isRead = isRead == null)
            },
        ).flow.map { data ->
            data.map { it.toSavedLinkDetailInfo() }
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
