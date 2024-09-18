package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.AIClassificationFoldersResponseModel
import com.mashup.dorabangs.data.model.AIClassificationPostsResponseModel
import com.mashup.dorabangs.data.model.AiClassificationMoveSinglePostRequestModel
import com.mashup.dorabangs.data.model.classification.AIClassificationAIPostListResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface AIClassificationService {

    @GET("classification/folders")
    suspend fun getAIClassificationsFolderList(): AIClassificationFoldersResponseModel

    @GET("classification/posts")
    suspend fun getAIClassificationPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String,
    ): AIClassificationAIPostListResponseModel

    @PATCH("classification/posts")
    suspend fun moveAllPostsToRecommendedFolder(
        @Query("suggestionFolderId") suggestionFolderId: String,
    )

    @PATCH("classification/posts/{postId}")
    suspend fun moveSinglePostToRecommendedFolder(
        @Path("postId") postId: String,
        @Body requestModel: AiClassificationMoveSinglePostRequestModel,
    )

    @GET("classification/posts/{folderId}")
    suspend fun getAIClassificationPostsByFolder(
        @Path("folderId") folderId: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("order") order: String? = null,
    ): AIClassificationAIPostListResponseModel

    @DELETE("classification/posts/{postId}")
    suspend fun deletePostFromAIClassification(
        @Path("postId") postId: String,
    )

    @GET("classification/count")
    suspend fun getAIClassificationCount(): Int
}
