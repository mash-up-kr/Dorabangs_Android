package com.mashup.dorabangs.data.network.service

import com.mashup.dorabangs.data.model.DoraResponse
import com.mashup.dorabangs.data.model.FolderListResponseModel
import com.mashup.dorabangs.data.model.FolderResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface FolderService {

    @GET("folders")
    suspend fun getFolders(): DoraResponse<FolderListResponseModel>

    @GET("folders/{id}")
    suspend fun getFolderById(@Path("id") folderId: String): DoraResponse<FolderResponseModel>
}