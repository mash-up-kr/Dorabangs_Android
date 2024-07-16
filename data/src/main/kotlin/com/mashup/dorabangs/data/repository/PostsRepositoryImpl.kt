package com.mashup.dorabangs.data.repository

import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postsRemoteDataSource: PostsRemoteDataSource
): PostsRepository {
    override suspend fun getPosts(
        page: Int?,
        limit: Int?,
        order: String?,
        favorite: Boolean?
    ): Posts = postsRemoteDataSource.getPosts(
        page = page,
        limit = limit,
        order = order,
        favorite = favorite,
    )

    override suspend fun saveLink(link: Link) =
        postsRemoteDataSource.saveLink(link)

    override suspend fun patchPostInfo(postId: String, postInfo: PostInfo) =
        postsRemoteDataSource.patchPostInfo(postId, postInfo)


    override suspend fun deletePost(postId: String) =
        postsRemoteDataSource.deletePost(postId)

    override suspend fun changePostFolder(postId: String, folderId: String) =
        postsRemoteDataSource.changePostFolder(postId, folderId)
}