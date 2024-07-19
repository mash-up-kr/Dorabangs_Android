package com.mashup.dorabangs.domain.usecase.aiclassification

import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import javax.inject.Inject

class GetAIClassificationFolderListUseCase @Inject constructor(
    private val aiClassificationRepository: AIClassificationRepository,
) {

    suspend operator fun invoke() =
        aiClassificationRepository.getAIClassificationsFolderList()
}
