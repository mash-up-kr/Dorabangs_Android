package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class GetAIClassificationPostsUseCase @Inject constructor(
    private val aiClassificationRepository: AIClassificationRepository,
) {

    suspend operator fun invoke(
        limit: Int,
        order: String,
    ) =
        aiClassificationRepository.getAIClassificationPosts(
            limit = limit,
            order = order,
        )
}
