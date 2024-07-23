package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.DoraCreateFolderModel
import com.mashup.dorabangs.domain.model.DoraCreateFolderResult
import com.mashup.dorabangs.domain.model.FolderType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderResponseModel(
    @SerialName("list")
    val createdResult: List<CreatedResult>,
    @SerialName("success")
    val success: Boolean = false,
)

@Serializable
data class CreatedResult(
    val createdAt: String = "",
    val id: String = "",
    val name: String = "",
    val type: String = ""
)

fun CreateFolderResponseModel.toDomain() = DoraCreateFolderModel(
    result = createdResult.map {
        DoraCreateFolderResult(
            createdAt = it.createdAt,
            id = it.id,
            name = it.name,
            type = FolderType.CUSTOM,
        )
    },
    isSuccess = true,
)
