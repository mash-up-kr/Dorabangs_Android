package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class GetAIClassificationPostsByFolderUseCase @Inject constructor(
    private val aiClassificationRepository: AIClassificationRepository,
) {

    suspend operator fun invoke(
        folderId: String,
        page: Int? = null,
        limit: Int? = null,
        order: Sort? = null,
    ) =
        aiClassificationRepository.getAIClassificationPostsByFolder(
            folderId = folderId,
            page = page,
            limit = limit,
            order = order?.query,
        )
}
