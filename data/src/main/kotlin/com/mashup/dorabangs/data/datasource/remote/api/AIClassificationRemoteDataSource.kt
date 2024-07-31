package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.AiClassificationMoveSinglePostResponseModel
import com.mashup.dorabangs.data.model.classification.AIClassificationAIPostListResponseModel
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.AIClassificationPosts

interface AIClassificationRemoteDataSource {

    suspend fun getAIClassificationsFolderList(): AIClassificationFolders

    suspend fun getAIClassificationPosts(
        page: Int,
        limit: Int,
        order: String,
    ): AIClassificationAIPostListResponseModel

    suspend fun moveAllPostsToRecommendedFolder(
        suggestionFolderId: String,
    ): AIClassificationPosts

    suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        page: Int? = null,
        limit: Int? = null,
        order: String? = null,
    ): AIClassificationPosts

    suspend fun deletePostFromAIClassification(
        postId: String,
    )

    suspend fun getAIClassificationCount(): Int

    suspend fun moveSinglePostToRecommendedFolder(
        postId: String,
        suggestionFolderId: String,
    ): AiClassificationMoveSinglePostResponseModel
}
