package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Posts

interface PostsRemoteDataSource {

    suspend fun getPosts(
        page: Int? = null,
        limit: Int? = null,
        order: String? = null,
        favorite: Boolean? = null,
    ): Posts

    suspend fun savePosts(
        link: Link
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