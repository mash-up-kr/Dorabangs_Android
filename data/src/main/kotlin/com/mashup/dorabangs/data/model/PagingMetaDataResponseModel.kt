package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.PagingInfo
import kotlinx.serialization.Serializable

@Serializable
data class PagingMetaDataResponseModel(
    val hasNext: Boolean = false,
    val total: Int = 0,
)

fun PagingMetaDataResponseModel.toPagingMetaDomain() = PagingInfo(
    hasNext = hasNext,
    total = total,
)
