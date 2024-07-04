package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateFolderInfo
import com.mashup.dorabangs.domain.model.CreatedFolder
import com.mashup.dorabangs.domain.model.FolderType
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderResponse(
    val id: String,
    val name: String,
    val type: String,
    val createAt: String,
)

fun CreateFolderResponse.toDomain(): CreatedFolder {
    return CreatedFolder(
        id = id,
        name = name,
        type = if (type == FolderType.CUSTOM.name) FolderType.CUSTOM else FolderType.DEFAULT,
        createAt = createAt,
    )
}
