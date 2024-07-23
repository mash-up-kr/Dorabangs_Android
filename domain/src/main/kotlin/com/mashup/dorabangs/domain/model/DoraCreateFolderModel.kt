package com.mashup.dorabangs.domain.model

data class DoraCreateFolderModel(
    val result: List<DoraCreateFolderResult> = listOf(),
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
)

fun DoraCreateFolderModel.toSampleResponse() = DoraSampleResponse(
    isSuccess = isSuccess,
    errorMsg = errorMsg,
)

data class DoraCreateFolderResult(
    val createdAt: String = "",
    val id: String = "",
    val name: String = "",
    val type: FolderType = FolderType.NOTHING,
)
