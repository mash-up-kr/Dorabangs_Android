package com.mashup.dorabangs.domain.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.AIClassificationPosts
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
    ): AIClassificationPosts

    suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        page: Int? = null,
        limit: Int? = null,
        order: String? = null,
    ): AIClassificationPosts

    suspend fun deletePostFromAIClassification(
        postId: String,
    ): Boolean

    suspend fun getAIClassificationCount(): Int
}
