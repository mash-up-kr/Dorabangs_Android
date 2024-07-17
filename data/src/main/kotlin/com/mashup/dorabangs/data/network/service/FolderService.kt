package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.CreateFolderRequestModel
import com.mashup.dorabangs.data.model.EditFolderNameRequestModel
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FolderService {

    @GET("folders")
    suspend fun getFolders(): FolderListResponseModel

    @GET("folders/{id}")
    suspend fun getFolderById(@Path("id") folderId: String): FolderResponseModel

    @POST("folders")
    suspend fun createFolder(
        @Body createFolderRequest: CreateFolderRequestModel,
    )

    @PATCH("folders/{folderId}")
    suspend fun editFolderName(
        @Path("folderId") folderId: String,
        @Body editFolderNameRequest: EditFolderNameRequestModel,
    )

    @DELETE("folders/{folderId}")
    suspend fun deleteFolder(
        @Path("folderId") folderId: String,
    )
}
