package com.mashup.dorabangs.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mashup.dorabangs.data.database.PostDatabase
import com.mashup.dorabangs.data.database.RemoteKeys
import com.mashup.dorabangs.data.database.toLocalEntity
import com.mashup.dorabangs.data.database.toPost
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.pagingsource.PostRemoteMediator
import com.mashup.dorabangs.data.utils.PAGING_SIZE
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val database: PostDatabase,
) : PostsRepository {

    override suspend fun getPosts(
        needFetchUpdate: Boolean,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<Post>> =
        doraPager(
            apiExecutor = { page ->
                postsRemoteDataSource.getPosts(
                    page = page,
                    order = order,
                    favorite = favorite,
                    isRead = isRead,
                )
            },
            totalCount = { total -> totalCount(total) },
        ).flow

    override suspend fun saveLink(link: Link) =
        postsRemoteDataSource.saveLink(link)

    override suspend fun patchPostInfo(postId: String, postInfo: PostInfo): DoraSampleResponse =
        runCatching {
            postsRemoteDataSource.patchPostInfo(postId, postInfo)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun deletePost(postId: String): DoraSampleResponse =
        runCatching {
            postsRemoteDataSource.deletePost(postId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun changePostFolder(postId: String, folderId: String): DoraSampleResponse =
        runCatching {
            postsRemoteDataSource.changePostFolder(postId, folderId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { throwable ->
            DoraSampleResponse(isSuccess = false, errorMsg = throwable.message.orEmpty())
        }

    override suspend fun getPostsCount(isRead: Boolean?): Int =
        postsRemoteDataSource.getPostsCount(isRead)

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPostsFromRemote(
        needFetchUpdate: Boolean,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<Post>> {
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
                getRemoteKey = { post -> RemoteKeys(postId = post.id) },
                apiExecutor = { page ->
                    postsRemoteDataSource.getPosts(
                        page = page,
                        order = order,
                        favorite = favorite,
                        isRead = isRead,
                    )
                },
            ),
            pagingSourceFactory = {
                if (order == "asc") {
                    database.postDao().getAllPostsOrderedByAsc(isRead = isRead == null)
                } else database.postDao().getAllPostsOrderedByDesc(isRead = isRead == null)
            },
        ).flow.map { data ->
            data.map { it.toPost() }
        }
    }

    override suspend fun deleteLocalPostItem(postId: String) {
        database.postDao().deletePostItem(postId)
    }

    override suspend fun updateBookMarkState(postId: String, isFavorite: Boolean) {
        database.postDao().updateFavoriteStatus(postId = postId, isFavorite = isFavorite)
    }
}
