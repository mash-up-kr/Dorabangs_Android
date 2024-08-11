package com.mashup.dorabangs.domain.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun getPosts(
        needFetchUpdate: Boolean,
        order: String? = null,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<Post>>

    suspend fun saveLink(
        link: Link,
    )

    suspend fun patchPostInfo(
        postId: String,
        postInfo: PostInfo,
    ): DoraSampleResponse

    suspend fun deletePost(postId: String): DoraSampleResponse

    suspend fun changePostFolder(
        postId: String,
        folderId: String,
    ): DoraSampleResponse

    suspend fun getPostsCount(isRead: Boolean? = null): Int

    suspend fun getPostsFromRemote(
        needFetchUpdate: Boolean,
        order: String? = null,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
        totalCount: (Int) -> Unit,
    ): Flow<PagingData<Post>>

    suspend fun deleteLocalPostItem(postId: String)

    suspend fun updateBookMarkState(postId: String, isFavorite: Boolean)
}
