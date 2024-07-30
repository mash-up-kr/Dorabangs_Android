package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.AIClassificationRemoteDataSource
import com.mashup.dorabangs.data.model.classification.toPagingDomain
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.AIClassificationPosts
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AIClassificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: AIClassificationRemoteDataSource,
) : AIClassificationRepository {

    override suspend fun getAIClassificationsFolderList(): AIClassificationFolders =
        remoteDataSource.getAIClassificationsFolderList()

    override suspend fun getAIClassificationPosts(
        limit: Int,
        order: String,
    ): Flow<PagingData<AIClassificationFeedPost>> =
        doraPager { page ->
            remoteDataSource.getAIClassificationPosts(
                page = page,
                limit = limit,
                order = order,
            ).toPagingDomain()
        }.flow

    override suspend fun moveAllPostsToRecommendedFolder(suggestionFolderId: String): AIClassificationPosts =
        remoteDataSource.moveAllPostsToRecommendedFolder(suggestionFolderId)

    override suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        page: Int?,
        limit: Int?,
        order: String?,
    ): AIClassificationPosts =
        remoteDataSource.getAIClassificationPostsByFolder(
            folderId = folderId,
            page = page,
            limit = limit,
            order = order,
        )

    override suspend fun deletePostFromAIClassification(postId: String) =
        remoteDataSource.deletePostFromAIClassification(postId)

    override suspend fun getAIClassificationCount() =
        remoteDataSource.getAIClassificationCount()
}
