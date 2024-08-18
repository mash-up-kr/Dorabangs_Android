package com.mashup.dorabangs.domain.model

import java.io.Serializable
import java.time.Instant
import java.time.temporal.ChronoUnit

data class FolderList(
    val defaultFolders: List<Folder>,
    val customFolders: List<Folder>,
) {
    fun toList() = defaultFolders + customFolders.sortedWith(
        compareBy<Folder> { -it.postCount }
            .thenByDescending { it.createdAt.convertCreatedSecond() }
    )
}

@kotlinx.serialization.Serializable
data class Folder(
    val id: String? = "",
    val name: String = "",
    private val type: String = "",
    val createdAt: String = "",
    val postCount: Int = 0,
) : Serializable {
    val folderType: FolderType
        get() = FolderType.valueOf(type.uppercase())
}

private fun String.convertCreatedSecond(): Int {
    runCatching {
        val givenDate = Instant.parse(this)
        val currentDate = Instant.now()
        return ChronoUnit.SECONDS.between(givenDate, currentDate).toInt()
    }
    return 0
}