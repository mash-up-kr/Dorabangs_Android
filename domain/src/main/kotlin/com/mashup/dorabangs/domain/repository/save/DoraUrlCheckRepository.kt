package com.mashup.dorabangs.domain.repository.save

import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel

interface DoraUrlCheckRepository {
    suspend fun checkUrl(urlLink: String): DoraSaveLinkDataModel
}