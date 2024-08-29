package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class MoveSinglePostToRecommendedFolderUseCase @Inject constructor(
    private val repository: AIClassificationRepository,
) {
    suspend operator fun invoke(
        postId: String,
        suggestionFolderId: String,
    ) = repository.moveSinglePostToRecommendedFolder(
        postId = postId,
        suggestionFolderId = suggestionFolderId,
    )
}
