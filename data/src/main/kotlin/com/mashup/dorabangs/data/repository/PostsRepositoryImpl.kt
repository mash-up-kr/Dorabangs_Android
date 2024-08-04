package com.mashup.dorabangs.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource,
) : PostsRepository {

    private val postListCache = HashMap<String, PagingData<Post>>()

    override suspend fun getPosts(
        needFetchUpdate: Boolean,
        cacheKey: String,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
        totalCount: (Int) -> Unit
    ): Flow<PagingData<Post>>  {
        val cachedData = postListCache[cacheKey]
        val pagingFlow = if (cachedData == null) {
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
            ).flow.onEach { pagingData -> postListCache[cacheKey] = pagingData }
        } else {
            flow {
                emit(cachedData)
            }
        }
       return pagingFlow
    }

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
}
