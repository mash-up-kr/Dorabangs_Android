package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateCompleteFolderInfo
import com.mashup.dorabangs.domain.model.FolderType
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderResponseModel(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val createAt: String = "",
)

fun CreateFolderResponseModel.toDomain(): CreateCompleteFolderInfo {
    return CreateCompleteFolderInfo(
        id = id,
        name = name,
        type = if (type == FolderType.CUSTOM.name) FolderType.CUSTOM else FolderType.DEFAULT,
        createAt = createAt,
    )
}
