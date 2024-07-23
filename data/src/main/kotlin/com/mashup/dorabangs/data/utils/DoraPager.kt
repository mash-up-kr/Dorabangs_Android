package com.mashup.dorabangs.data.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mashup.dorabangs.data.pagingsource.DoraPagingSource
import com.mashup.dorabangs.domain.model.PageData

fun <T : Any> doraPager(
    pageSize: Int = PAGING_SIZE,
    apiExecutor: suspend (Int) -> PageData<List<T>>,
): Pager<Int, T> = Pager(
    config = PagingConfig(
        pageSize = pageSize,
        enablePlaceholders = false,
        initialLoadSize = PAGING_SIZE,
    ),
    pagingSourceFactory = { DoraPagingSource(apiExecutor) },
)

const val PAGING_SIZE = 10
