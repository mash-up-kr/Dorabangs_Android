package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.model.toData
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.network.service.PostsService
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Posts
import javax.inject.Inject

class PostsRemoteDataSourceImpl @Inject constructor(
    private val postsService: PostsService,
) : PostsRemoteDataSource {

    override suspend fun getPosts(
        page: Int?,
        limit: Int?,
        order: String?,
        favorite: Boolean?,
    ): Posts =
        postsService.getPosts(
            page = page,
            limit = limit,
            order = order,
            favorite = favorite,
        ).toDomain()

    override suspend fun saveLink(link: Link) =
        postsService.saveLink(link.toData())

    override suspend fun patchPostInfo(postId: String, postInfo: PostInfo) =
        postsService.patchPostInfo(postId, postInfo.toDomain())

    override suspend fun deletePost(postId: String) =
        postsService.deletePost(postId)

    override suspend fun changePostFolder(postId: String, folderId: String) =
        postsService.changePostFolder(postId, folderId)
}
