package com.mashup.dorabangs.domain.usecase.folder

import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.repository.FolderRepository
import java.util.Locale
import javax.inject.Inject

class GetPostsFromFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(
        folderId: String?,
        page: Int,
        order: String,
        limit: Int,
        isRead: Boolean?,
    ): Posts {
        return folderRepository.getPostPageFromFolder(
            folderId = folderId,
            page = page,
            order = order.lowercase(Locale.ROOT),
            limit = limit,
            isRead = isRead,
        )
    }
}