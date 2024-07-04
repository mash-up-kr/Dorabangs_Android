package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateFolderInfo
import com.mashup.dorabangs.domain.model.FolderType
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderResponse(
    val id: String,
    val name: String,
    val type: String,
    val createAt: String,
)

fun CreateFolderResponse.toDomain(): CreateFolderInfo {
    return CreateFolderInfo(
        id = id,
        name = name,
        type = if (type == FolderType.CUSTOM.name) FolderType.CUSTOM else FolderType.DEFAULT,
        createAt = createAt,
    )
}
