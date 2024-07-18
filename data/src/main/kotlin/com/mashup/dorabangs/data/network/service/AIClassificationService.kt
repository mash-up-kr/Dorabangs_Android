package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.AIClassificationFoldersResponseModel
import com.mashup.dorabangs.data.model.AIClassificationPostsResponseModel
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
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("order") order: String? = null,
    ): AIClassificationPostsResponseModel

    @PATCH("classification/posts")
    suspend fun moveAllPostsToRecommendedFolder(
        @Query("suggestionFolderId") suggestionFolderId: String,
    ): AIClassificationPostsResponseModel

    @GET("classification/posts/{folderId}")
    suspend fun getAIClassificationPostsByFolder(
        @Path("folderId") folderId: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("order") order: String? = null,
    ): AIClassificationPostsResponseModel

    @DELETE("classification/posts/{postId}")
    suspend fun deletePostFromAIClassification(
        @Path("postId") postId: String,
    )
}
