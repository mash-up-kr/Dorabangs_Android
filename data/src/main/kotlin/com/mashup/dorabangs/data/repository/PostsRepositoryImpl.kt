package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource,
) : PostsRepository {
    override suspend fun getPosts(
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
    ): Flow<PagingData<Post>> =
        doraPager { page ->
            postsRemoteDataSource.getPosts(
                page = page,
                order = order,
                favorite = favorite,
                isRead = isRead,
            )
        }.flow

    override suspend fun saveLink(link: Link) =
        postsRemoteDataSource.saveLink(link)

    override suspend fun patchPostInfo(postId: String, postInfo: PostInfo) = runCatching {
        postsRemoteDataSource.patchPostInfo(postId, postInfo)
        DoraSampleResponse(isSuccess = true)
    }.getOrElse {
        DoraSampleResponse(isSuccess = false)
    }

    override suspend fun deletePost(postId: String) =
        postsRemoteDataSource.deletePost(postId)

    override suspend fun changePostFolder(postId: String, folderId: String) =
        postsRemoteDataSource.changePostFolder(postId, folderId)

    override suspend fun getPostsCount(isRead: Boolean?): Int =
        postsRemoteDataSource.getPostsCount(isRead)
}
