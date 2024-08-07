package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.utils.doraConvertKey
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource,
) : PostsRepository {

    private val pagingListCache = HashMap<String, PageData<List<Post>>>()

    override suspend fun getPosts(
        needFetchUpdate: Boolean,
        cacheKey: String,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<Post>> =
        doraPager(
            needFetchUpdate = needFetchUpdate,
            cachedList = pagingListCache,
            cacheKey = cacheKey,
            apiExecutor = { page ->
                postsRemoteDataSource.getPosts(
                    page = page,
                    order = order,
                    favorite = favorite,
                    isRead = isRead,
                )
            },
            totalCount = { total -> totalCount(total) },
            cachingData = { item, page ->
                pagingListCache[doraConvertKey(page, cacheKey)] = item
            },
        ).flow

    override fun updatePostItem(
        page: Int,
        cacheKey: String,
        cachedKeyList: List<String>,
        item: Post,
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
