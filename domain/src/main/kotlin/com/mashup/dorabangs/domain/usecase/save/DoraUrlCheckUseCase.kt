package com.mashup.dorabangs.domain.usecase.save

import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import javax.inject.Inject

class DoraUrlCheckUseCase @Inject constructor(
    private val urlCheckRepository: DoraUrlCheckRepository,
) {
    suspend operator fun invoke(urlLink: String): DoraSaveLinkDataModel {
        return urlCheckRepository.checkUrl(urlLink = urlLink)
    }
}
