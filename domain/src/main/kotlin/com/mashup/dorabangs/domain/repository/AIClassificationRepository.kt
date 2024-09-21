package com.mashup.dorabangs.domain.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import kotlinx.coroutines.flow.Flow

interface AIClassificationRepository {

    suspend fun getAIClassificationsFolderList(): AIClassificationFolders

    suspend fun getAIClassificationPosts(
        limit: Int,
        order: String,
    ): Flow<PagingData<AIClassificationFeedPost>>

    suspend fun moveAllPostsToRecommendedFolder(
        suggestionFolderId: String,
    ): DoraSampleResponse

    suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        limit: Int? = null,
        order: String? = null,
    ): Flow<PagingData<AIClassificationFeedPost>>

    suspend fun deletePostFromAIClassification(
        postId: String,
    ): DoraSampleResponse

    suspend fun getAIClassificationCount(): Int

    suspend fun moveSinglePostToRecommendedFolder(
        postId: String,
        suggestionFolderId: String,
    ): DoraSampleResponse
}
