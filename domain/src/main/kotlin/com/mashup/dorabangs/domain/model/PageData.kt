package com.mashup.dorabangs.domain.model

data class PageData<T>(
    val data: T,
    val pagingInfo: PagingInfo,
)
