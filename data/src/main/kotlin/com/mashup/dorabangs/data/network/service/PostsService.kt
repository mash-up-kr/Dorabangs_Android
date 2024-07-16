package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.LinkRequestModel
import com.mashup.dorabangs.data.model.PostInfoRequestModel
import com.mashup.dorabangs.data.model.PostsResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsService {

    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int? = null,
        @Query("page") limit: Int? = null,
        @Query("page") order: String? = null,
        @Query("page") favorite: Boolean? = null,
    ): PostsResponseModel

    @POST("posts")
    suspend fun saveLink(@Body link: LinkRequestModel)

    @PATCH("posts/{postId}")
    suspend fun patchPostInfo(
        @Path("postId") postId: String,
        @Body postInfo: PostInfoRequestModel,
    )

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: String)

    @PATCH("posts/{postId}/move")
    suspend fun changePostFolder(
        @Path("postId") postId: String,
        @Body folderId: String,
    )
}
