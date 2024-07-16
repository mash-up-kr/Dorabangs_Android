package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.EditCompleteFolderInfo
import com.mashup.dorabangs.domain.model.EditedFolderInfo
import com.mashup.dorabangs.domain.model.FolderType
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameResponseModel(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val createAt: String = "",
)

fun EditFolderNameResponseModel.CompleteEditFolder(): EditCompleteFolderInfo {
    return EditCompleteFolderInfo(
        completeFolderInfo = EditedFolderInfo(
            id = id,
            name = name,
            type = if (type == FolderType.CUSTOM.name) FolderType.CUSTOM else FolderType.DEFAULT,
            createAt = createAt,
        ),
    )
}

fun String.FailEditFolder(): EditCompleteFolderInfo {
    return EditCompleteFolderInfo(
        errorMsg = this,
    )
}
