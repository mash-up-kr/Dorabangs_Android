package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class GetAIClassificationPostsUseCase @Inject constructor(
    private val aiClassificationRepository: AIClassificationRepository,
) {

    suspend operator fun invoke(
        page: Int? = null,
        limit: Int? = null,
        order: Sort? = null,
    ) =
        aiClassificationRepository.getAIClassificationPosts(
            page = page,
            limit = limit,
            order = order?.query,
        )
}
