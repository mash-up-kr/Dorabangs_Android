package com.mashup.dorabangs.data.datasource.remote.impl

import com.mashup.dorabangs.data.datasource.remote.api.AIClassificationRemoteDataSource
import com.mashup.dorabangs.data.model.AiClassificationMoveSinglePostRequestModel
import com.mashup.dorabangs.data.model.AiClassificationMoveSinglePostResponseModel
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.network.service.AIClassificationService
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.AIClassificationPosts
import javax.inject.Inject

class AIClassificationRemoteDataSourceImpl @Inject constructor(
    private val service: AIClassificationService,
) : AIClassificationRemoteDataSource {

    override suspend fun getAIClassificationsFolderList(): AIClassificationFolders =
        service.getAIClassificationsFolderList()
            .toDomain()

    override suspend fun getAIClassificationPosts(
        page: Int?,
        limit: Int?,
        order: String?,
    ): AIClassificationPosts =
        service.getAIClassificationPosts(
            page = page,
            limit = limit,
            order = order,
        ).toDomain()

    override suspend fun moveAllPostsToRecommendedFolder(suggestionFolderId: String) =
        service.moveAllPostsToRecommendedFolder(
            suggestionFolderId = suggestionFolderId,
        ).toDomain()

    override suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        page: Int?,
        limit: Int?,
        order: String?,
    ): AIClassificationPosts =
        service.getAIClassificationPostsByFolder(
            folderId = folderId,
            page = page,
            limit = limit,
            order = order,
        ).toDomain()

    override suspend fun deletePostFromAIClassification(postId: String) =
        service.deletePostFromAIClassification(
            postId = postId,
        )

    override suspend fun getAIClassificationCount() =
        service.getAIClassificationCount()

    override suspend fun moveSinglePostToRecommendedFolder(
        postId: String,
        suggestionFolderId: String,
    ): AiClassificationMoveSinglePostResponseModel = service.moveSinglePostToRecommendedFolder(
        postId = postId,
        requestModel = AiClassificationMoveSinglePostRequestModel(
            suggestionFolderId = suggestionFolderId,
        ),
    )
}
