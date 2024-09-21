package com.mashup.dorabangs.data.datasource.remote.api

import com.mashup.dorabangs.data.model.classification.AIClassificationAIPostListResponseModel
import com.mashup.dorabangs.domain.model.AIClassificationFolders

interface AIClassificationRemoteDataSource {

    suspend fun getAIClassificationsFolderList(): AIClassificationFolders

    suspend fun getAIClassificationPosts(
        page: Int,
        limit: Int,
        order: String,
    ): AIClassificationAIPostListResponseModel

    suspend fun moveAllPostsToRecommendedFolder(
        suggestionFolderId: String,
    )

    suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        page: Int? = null,
        limit: Int? = null,
        order: String? = null,
    ): AIClassificationAIPostListResponseModel

    suspend fun deletePostFromAIClassification(
        postId: String,
    )

    suspend fun getAIClassificationCount(): Int

    suspend fun moveSinglePostToRecommendedFolder(
        postId: String,
        suggestionFolderId: String,
    )
}
