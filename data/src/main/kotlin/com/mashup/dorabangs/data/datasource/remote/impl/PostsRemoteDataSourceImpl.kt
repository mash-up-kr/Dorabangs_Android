package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.model.ChangePostFolderIdRequestModel
import com.mashup.dorabangs.data.model.PostsResponseModel
import com.mashup.dorabangs.data.model.toData
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.model.toPagingDomain
import com.mashup.dorabangs.data.network.service.PostsService
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.PostInfo
import javax.inject.Inject

class PostsRemoteDataSourceImpl @Inject constructor(
    private val postsService: PostsService,
) : PostsRemoteDataSource {

    override suspend fun getPosts(
        page: Int?,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?,
    ): PageData<List<Post>> =
        postsService.getPosts(
            page = page,
            order = order,
            favorite = favorite,
            isRead = isRead,
        ).toPagingDomain()

    override suspend fun saveLink(link: Link) =
        postsService.saveLink(link.toData())

    override suspend fun patchPostInfo(postId: String, postInfo: PostInfo) =
        postsService.patchPostInfo(postId, postInfo.toDomain())

    override suspend fun deletePost(postId: String) =
        postsService.deletePost(postId)

    override suspend fun changePostFolder(postId: String, folderId: String) =
        postsService.changePostFolder(postId, ChangePostFolderIdRequestModel(folderId = folderId))

    override suspend fun getPostsCount(isRead: Boolean?): Int =
        postsService.getPostsCount(isRead)

    override suspend fun getPostPage(
        page: Int?,
        order: String?,
        favorite: Boolean?,
        isRead: Boolean?
    ): PostsResponseModel =
        postsService.getPosts(
            page = page,
            order = order,
            favorite = favorite,
            isRead = isRead
        )
}
