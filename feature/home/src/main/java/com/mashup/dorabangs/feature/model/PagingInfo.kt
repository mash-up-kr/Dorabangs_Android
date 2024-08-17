package com.mashup.dorabangs.feature.model

import com.mashup.dorabangs.domain.model.Sort

data class PagingInfo(
    val nextPage: Int = 0,
    val hasNext: Boolean = false,
    val order: String? = null,
    val favorite: Boolean? = null,
    val isRead: Boolean? = null,
) {
    companion object {
        fun getDefault(cacheKey: String) = PagingInfo(
            nextPage = 1,
            hasNext = true,
            order = Sort.DESC.query,
            favorite = if (cacheKey == "favorite") true else null,
        )
    }
}
