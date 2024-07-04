package com.mashup.dorabangs.data.repository.save

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckResponse
import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel

fun DoraUrlCheckResponse.toDataModel() = DoraSaveLinkDataModel(
    urlLink = urlLink.orEmpty(),
    title = title.orEmpty(),
    thumbnailUrl = thumbnailUrl.orEmpty(),
    isShortLink = isShortLink ?: true
)