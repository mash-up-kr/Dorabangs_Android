package com.mashup.dorabangs.data.repository

import androidx.paging.PagingData
import com.mashup.dorabangs.data.datasource.remote.api.AIClassificationRemoteDataSource
import com.mashup.dorabangs.data.model.classification.toPagingDomain
import com.mashup.dorabangs.data.utils.doraPager
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.AIClassificationPosts
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AIClassificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: AIClassificationRemoteDataSource,
) : AIClassificationRepository {

    override suspend fun getAIClassificationsFolderList(): AIClassificationFolders =
        remoteDataSource.getAIClassificationsFolderList()

    override suspend fun getAIClassificationPosts(
        limit: Int,
        order: String,
    ): Flow<PagingData<AIClassificationFeedPost>> =
        doraPager(
            apiExecutor = { page ->
                remoteDataSource.getAIClassificationPosts(
                    page = page,
                    limit = limit,
                    order = order,
                ).toPagingDomain()
            },
        ).flow

    override suspend fun moveAllPostsToRecommendedFolder(suggestionFolderId: String): DoraSampleResponse {
        return kotlin.runCatching {
            remoteDataSource.moveAllPostsToRecommendedFolder(suggestionFolderId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse {
            DoraSampleResponse(isSuccess = false, errorMsg = it.message.orEmpty())
        }
    }

    override suspend fun getAIClassificationPostsByFolder(
        folderId: String,
        limit: Int?,
        order: String?,
    ): Flow<PagingData<AIClassificationFeedPost>> =
        doraPager(
            apiExecutor = { page ->
                remoteDataSource.getAIClassificationPostsByFolder(
                    folderId = folderId,
                    page = page,
                    limit = limit,
                    order = order,
                ).toPagingDomain()
            },
        ).flow

    override suspend fun deletePostFromAIClassification(postId: String): DoraSampleResponse {
        return kotlin.runCatching {
            remoteDataSource.deletePostFromAIClassification(postId)
            DoraSampleResponse(isSuccess = true)
        }.getOrElse { DoraSampleResponse(isSuccess = false, errorMsg = it.message.orEmpty()) }
    }

    override suspend fun getAIClassificationCount() =
        remoteDataSource.getAIClassificationCount()

    override suspend fun moveSinglePostToRecommendedFolder(
        postId: String,
        suggestionFolderId: String,
    ) = kotlin.runCatching {
        remoteDataSource.moveSinglePostToRecommendedFolder(
            postId = postId,
            suggestionFolderId = suggestionFolderId,
        )
        DoraSampleResponse(isSuccess = true)
    }.getOrElse {
        DoraSampleResponse(isSuccess = false, errorMsg = it.message.orEmpty())
    }
}
