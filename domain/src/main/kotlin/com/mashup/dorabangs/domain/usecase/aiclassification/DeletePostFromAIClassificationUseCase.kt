package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class DeletePostFromAIClassificationUseCase @Inject constructor(
    private val aiClassificationRepository: AIClassificationRepository,
) {

    suspend operator fun invoke(
        postId: String,
    ): DoraSampleResponse {
        return aiClassificationRepository.deletePostFromAIClassification(
            postId = postId,
        )
    }
}
