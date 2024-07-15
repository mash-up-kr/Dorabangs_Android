package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.CreateFolderRequest
import com.mashup.dorabangs.data.model.CreateFolderResponse
import com.mashup.dorabangs.data.model.DoraResponse
import com.mashup.dorabangs.data.model.EditFolderNameRequest
import com.mashup.dorabangs.data.model.EditFolderNameResponse
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FolderService {

    @GET("folders")
    suspend fun getFolders(): FolderListResponseModel

    @GET("folders/{id}")
    suspend fun getFolderById(@Path("id") folderId: String): FolderResponseModel

    @POST("folders")
    suspend fun createFolder(
        @Body createFolderRequest: CreateFolderRequest,
    )

    @PATCH("folders/{folderId}")
    suspend fun editFolderName(
        @Path("id") folderId: String,
        @Body editFolderNameRequest: EditFolderNameRequest,
    ): EditFolderNameResponse
}
