package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo

interface PostsRemoteDataSource {

    suspend fun getPosts(
        page: Int? = null,
        order: String? = null,
        favorite: Boolean? = null,
    ): PageData<List<Post>>

    suspend fun saveLink(
        link: Link,
    )

    suspend fun patchPostInfo(
        postId: String,
        postInfo: PostInfo,
    )

    suspend fun deletePost(postId: String)

    suspend fun changePostFolder(
        postId: String,
        folderId: String,
    )
}
