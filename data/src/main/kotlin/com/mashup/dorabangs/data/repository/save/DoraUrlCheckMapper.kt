package com.mashup.dorabangs.data.repository.save

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckResponse
import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel

fun DoraUrlCheckResponse.toDataModel() = DoraSaveLinkDataModel(
    urlLink = urlLink,
    title = title,
    thumbnailUrl = thumbnailUrl,
    isShortLink = isShortLink,
    isError = isError,
)
